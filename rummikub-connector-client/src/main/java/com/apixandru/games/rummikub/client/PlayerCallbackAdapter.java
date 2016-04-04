package com.apixandru.games.rummikub.client;

import com.apixandru.games.rummikub.api.BoardCallback;
import com.apixandru.games.rummikub.api.GameEventListener;
import com.apixandru.games.rummikub.api.PlayerCallback;
import com.apixandru.games.rummikub.brotocol.Packet;
import com.apixandru.games.rummikub.brotocol.PacketHandler;
import com.apixandru.games.rummikub.brotocol.PacketReader;
import com.apixandru.games.rummikub.brotocol.connect.WaitingRoomListener;
import com.apixandru.games.rummikub.brotocol.connect.server.PacketPlayerJoined;
import com.apixandru.games.rummikub.brotocol.connect.server.PacketPlayerLeft;
import com.apixandru.games.rummikub.brotocol.connect.server.PacketPlayerStart;
import com.apixandru.games.rummikub.brotocol.game.server.PacketCardPlaced;
import com.apixandru.games.rummikub.brotocol.game.server.PacketCardRemoved;
import com.apixandru.games.rummikub.brotocol.game.server.PacketGameOver;
import com.apixandru.games.rummikub.brotocol.game.server.PacketNewTurn;
import com.apixandru.games.rummikub.brotocol.game.server.PacketReceiveCard;
import com.apixandru.games.rummikub.client.game.CardPlacedHandler;
import com.apixandru.games.rummikub.client.game.CardRemovedHandler;
import com.apixandru.games.rummikub.client.game.GameOverHandler;
import com.apixandru.games.rummikub.client.game.NewTurnHandler;
import com.apixandru.games.rummikub.client.game.ReceiveCardHandler;
import com.apixandru.games.rummikub.client.waiting.PlayerJoinedHandler;
import com.apixandru.games.rummikub.client.waiting.PlayerLeftHandler;
import com.apixandru.games.rummikub.client.waiting.PlayerStartHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @param <H> hint type
 * @author Alexandru-Constantin Bledea
 * @since January 04, 2016
 */
final class PlayerCallbackAdapter<H> implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(PlayerCallbackAdapter.class);

    private final List<BoardCallback> boardCallbacks = new CopyOnWriteArrayList<>();
    private final List<WaitingRoomListener> waitingRoomListeners = new CopyOnWriteArrayList<>();
    private final List<ConnectionListener> connectionListeners = new CopyOnWriteArrayList<>();
    private final List<PlayerCallback<H>> playerCallbacks = new CopyOnWriteArrayList<>();
    private final List<GameEventListener> gameEventListeners = new CopyOnWriteArrayList<>();

    private final PacketReader reader;
    private final Map<Class, PacketHandler> handlers = new HashMap<>();

    private final AtomicBoolean continueReading = new AtomicBoolean(true);

    PlayerCallbackAdapter(final List<H> hints, final PacketReader reader) {
        this.reader = reader;

        handlers.put(PacketPlayerJoined.class, new PlayerJoinedHandler(waitingRoomListeners));
        handlers.put(PacketPlayerLeft.class, new PlayerLeftHandler(waitingRoomListeners));
        handlers.put(PacketPlayerStart.class, new PlayerStartHandler(waitingRoomListeners));

        handlers.put(PacketCardPlaced.class, new CardPlacedHandler(boardCallbacks));
        handlers.put(PacketCardRemoved.class, new CardRemovedHandler(boardCallbacks));
        handlers.put(PacketNewTurn.class, new NewTurnHandler(gameEventListeners));
        handlers.put(PacketGameOver.class, new GameOverHandler(gameEventListeners, continueReading));
        handlers.put(PacketReceiveCard.class, new ReceiveCardHandler<>(playerCallbacks, hints));
    }

    @Override
    @SuppressWarnings("unchecked")
    public void run() {
        try (final Closeable closeMe = this.reader) {
            while (continueReading.get()) {
                final Packet input = reader.readPacket();
                final PacketHandler packetHandler = handlers.get(input.getClass());
                packetHandler.handle(input);
            }
        } catch (final EOFException e) {
            log.debug("Server was shutdown?", e);
            connectionListeners.forEach(ConnectionListener::onConnectionLost);
        } catch (final IOException e) {
            log.error("Unknown exception", e);
            connectionListeners.forEach(ConnectionListener::onConnectionLost);
        }
    }

    public void addGameEventListener(final GameEventListener gameEventListener) {
        this.gameEventListeners.add(gameEventListener);
    }

    public void addPlayerCallback(final PlayerCallback<H> playerCallback) {
        this.playerCallbacks.add(playerCallback);
    }

    public void addConnectionListener(final ConnectionListener connectionListener) {
        this.connectionListeners.add(connectionListener);
    }

    public void addBoardCallback(final BoardCallback boardCallback) {
        this.boardCallbacks.add(boardCallback);
    }

    public void addWaitingRoomListener(final WaitingRoomListener waitingRoomListener) {
        this.waitingRoomListeners.add(waitingRoomListener);
    }

}
