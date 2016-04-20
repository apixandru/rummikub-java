package com.apixandru.games.rummikub.client;

import com.apixandru.games.rummikub.api.BoardListener;
import com.apixandru.games.rummikub.api.GameEventListener;
import com.apixandru.games.rummikub.api.PlayerCallback;
import com.apixandru.games.rummikub.brotocol.Packet;
import com.apixandru.games.rummikub.brotocol.PacketHandler;
import com.apixandru.games.rummikub.brotocol.SocketWrapper;
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
import com.apixandru.games.rummikub.client.waiting.PlayerStartHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @param <H> hint type
 * @author Alexandru-Constantin Bledea
 * @since January 04, 2016
 */
public final class PlayerCallbackAdapter<H> implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(PlayerCallbackAdapter.class);

    final SocketWrapper socketWrapper;

    private final Reference<BoardListener> boardListener = new Reference<>();
    private final Reference<ConnectionListener> connectionListener = new Reference<>();
    private final Reference<PlayerCallback<H>> playerCallback = new Reference<>();
    private final Reference<GameEventListener> gameEventListener = new Reference<>();

    private final Map<Class, PacketHandler> handlers = new HashMap<>();

    private final AtomicBoolean continueReading = new AtomicBoolean(true);

    public PlayerCallbackAdapter(final List<H> hints, final RummikubConnector connector) {
        this.socketWrapper = connector.socketWrapper;

        handlers.put(PacketPlayerStart.class, new PlayerStartHandler<>(connector.stateChangeListener));

        handlers.put(PacketCardPlaced.class, new CardPlacedHandler(boardListener));
        handlers.put(PacketCardRemoved.class, new CardRemovedHandler(boardListener));
        handlers.put(PacketNewTurn.class, new NewTurnHandler(gameEventListener));
        handlers.put(PacketGameOver.class, new GameOverHandler(gameEventListener, continueReading));
        handlers.put(PacketReceiveCard.class, new ReceiveCardHandler<>(playerCallback, hints));
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
            connectionListener.get().onConnectionLost();
        } catch (final IOException e) {
            log.error("Unknown exception", e);
            connectionListener.get().onConnectionLost();
        }
    }

    public void setGameEventListener(final GameEventListener gameEventListener) {
        this.gameEventListener.set(gameEventListener);
    }

    public void setPlayerCallback(final PlayerCallback<H> playerCallback) {
        this.playerCallback.set(playerCallback);
    }

    public void setConnectionListener(final ConnectionListener connectionListener) {
        this.connectionListener.set(connectionListener);
    }

    public void setBoardListener(final BoardListener boardListener) {
        this.boardListener.set(boardListener);
    }

}
