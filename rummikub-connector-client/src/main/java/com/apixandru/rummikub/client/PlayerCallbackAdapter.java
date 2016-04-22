package com.apixandru.rummikub.client;

import com.apixandru.rummikub.api.BoardListener;
import com.apixandru.rummikub.api.GameEventListener;
import com.apixandru.rummikub.api.PlayerCallback;
import com.apixandru.rummikub.brotocol.Packet;
import com.apixandru.rummikub.brotocol.PacketHandler;
import com.apixandru.rummikub.brotocol.SocketWrapper;
import com.apixandru.rummikub.brotocol.game.server.PacketCardPlaced;
import com.apixandru.rummikub.brotocol.game.server.PacketCardRemoved;
import com.apixandru.rummikub.brotocol.game.server.PacketGameOver;
import com.apixandru.rummikub.brotocol.game.server.PacketNewTurn;
import com.apixandru.rummikub.brotocol.game.server.PacketReceiveCard;
import com.apixandru.rummikub.brotocol.util.Reference;
import com.apixandru.rummikub.client.game.CardPlacedHandler;
import com.apixandru.rummikub.client.game.CardRemovedHandler;
import com.apixandru.rummikub.client.game.GameOverHandler;
import com.apixandru.rummikub.client.game.NewTurnHandler;
import com.apixandru.rummikub.client.game.ReceiveCardHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 04, 2016
 */
public final class PlayerCallbackAdapter implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(PlayerCallbackAdapter.class);

    final SocketWrapper socketWrapper;

    private final Reference<BoardListener> boardListener = new Reference<>();
    private final Reference<ConnectionListener> connectionListener = new Reference<>();
    private final Reference<PlayerCallback<Integer>> playerCallback = new Reference<>();
    private final Reference<GameEventListener> gameEventListener = new Reference<>();

    private final Map<Class, PacketHandler> handlers = new HashMap<>();

    private final AtomicBoolean continueReading = new AtomicBoolean(true);

    public PlayerCallbackAdapter(final RummikubConnector connector) {
        this.socketWrapper = connector.socketWrapper;

        handlers.put(PacketCardPlaced.class, new CardPlacedHandler(boardListener));
        handlers.put(PacketCardRemoved.class, new CardRemovedHandler(boardListener));
        handlers.put(PacketNewTurn.class, new NewTurnHandler(gameEventListener));
        handlers.put(PacketGameOver.class, new GameOverHandler(gameEventListener, continueReading));
        handlers.put(PacketReceiveCard.class, new ReceiveCardHandler(playerCallback));
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

}
