package com.apixandru.games.rummikub.client;

import com.apixandru.games.rummikub.api.BoardListener;
import com.apixandru.games.rummikub.api.GameEventListener;
import com.apixandru.games.rummikub.api.PlayerCallback;
import com.apixandru.games.rummikub.brotocol.Packet;
import com.apixandru.games.rummikub.brotocol.PacketHandler;
import com.apixandru.games.rummikub.brotocol.SocketWrapper;
import com.apixandru.games.rummikub.brotocol.connect.server.PacketPlayerJoined;
import com.apixandru.games.rummikub.brotocol.connect.server.PacketPlayerLeft;
import com.apixandru.games.rummikub.brotocol.connect.server.PacketPlayerStart;
import com.apixandru.games.rummikub.brotocol.game.server.PacketCardPlaced;
import com.apixandru.games.rummikub.brotocol.game.server.PacketCardRemoved;
import com.apixandru.games.rummikub.brotocol.game.server.PacketGameOver;
import com.apixandru.games.rummikub.brotocol.game.server.PacketNewTurn;
import com.apixandru.games.rummikub.brotocol.game.server.PacketReceiveCard;
import com.apixandru.games.rummikub.brotocol.util.Reference;
import com.apixandru.games.rummikub.client.game.CardPlacedHandler;
import com.apixandru.games.rummikub.client.game.CardRemovedHandler;
import com.apixandru.games.rummikub.client.game.GameOverHandler;
import com.apixandru.games.rummikub.client.game.NewTurnHandler;
import com.apixandru.games.rummikub.client.game.ReceiveCardHandler;
import com.apixandru.games.rummikub.client.waiting.PlayerJoinedHandler;
import com.apixandru.games.rummikub.client.waiting.PlayerLeftHandler;
import com.apixandru.games.rummikub.client.waiting.PlayerStartHandler;
import com.apixandru.rummikub.waiting.WaitingRoomListener;
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
public final class PlayerCallbackAdapter<H> implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(PlayerCallbackAdapter.class);

    final SocketWrapper socketWrapper;

    private final Reference<BoardListener> boardListeners = new Reference<>();
    private final List<WaitingRoomListener> waitingRoomListeners = new CopyOnWriteArrayList<>();
    private final Reference<ConnectionListener> connectionListeners = new Reference<>();
    private final Reference<PlayerCallback<H>> playerCallbacks = new Reference<>();
    private final List<GameEventListener> gameEventListeners = new CopyOnWriteArrayList<>();

    private final Map<Class, PacketHandler> handlers = new HashMap<>();

    private final AtomicBoolean continueReading = new AtomicBoolean(true);

    public PlayerCallbackAdapter(final List<H> hints, final RummikubConnector<H> connector) {
        this.socketWrapper = connector.socketWrapper;

        handlers.put(PacketPlayerJoined.class, new PlayerJoinedHandler(waitingRoomListeners));
        handlers.put(PacketPlayerLeft.class, new PlayerLeftHandler(waitingRoomListeners));
        handlers.put(PacketPlayerStart.class, new PlayerStartHandler<>(connector.stateChangeListener));

        handlers.put(PacketCardPlaced.class, new CardPlacedHandler(boardListeners));
        handlers.put(PacketCardRemoved.class, new CardRemovedHandler(boardListeners));
        handlers.put(PacketNewTurn.class, new NewTurnHandler(gameEventListeners));
        handlers.put(PacketGameOver.class, new GameOverHandler(gameEventListeners, continueReading));
        handlers.put(PacketReceiveCard.class, new ReceiveCardHandler<>(playerCallbacks, hints));
    }

    @Override
    @SuppressWarnings("unchecked")
    public void run() {
        try (final Closeable closeMe = this.socketWrapper) {
            while (continueReading.get()) {
                final Packet input = socketWrapper.readPacket();
                final PacketHandler packetHandler = handlers.get(input.getClass());
                packetHandler.handle(input);
            }
        } catch (final EOFException e) {
            log.debug("Server was shutdown?", e);
            connectionListeners.get().onConnectionLost();
        } catch (final IOException e) {
            log.error("Unknown exception", e);
            connectionListeners.get().onConnectionLost();
        }
    }

    public void addGameEventListener(final GameEventListener gameEventListener) {
        this.gameEventListeners.add(gameEventListener);
    }

    public void addPlayerCallback(final PlayerCallback<H> playerCallback) {
        this.playerCallbacks.set(playerCallback);
    }

    public void addConnectionListener(final ConnectionListener connectionListener) {
        this.connectionListeners.set(connectionListener);
    }

    public void addBoardListener(final BoardListener boardListener) {
        this.boardListeners.set(boardListener);
    }

    public void addWaitingRoomListener(final WaitingRoomListener waitingRoomListener) {
        this.waitingRoomListeners.add(waitingRoomListener);
    }

}
