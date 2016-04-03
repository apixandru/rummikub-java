package com.apixandru.games.rummikub.client;

import com.apixandru.games.rummikub.api.BoardCallback;
import com.apixandru.games.rummikub.api.GameEventListener;
import com.apixandru.games.rummikub.api.PlayerCallback;
import com.apixandru.games.rummikub.brotocol.Packet;
import com.apixandru.games.rummikub.brotocol.PacketHandler;
import com.apixandru.games.rummikub.brotocol.PacketReader;
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

    final List<GameEventListener> gameEventListeners = new CopyOnWriteArrayList<>();
    final List<BoardCallback> boardCallbacks = new CopyOnWriteArrayList<>();
    final List<PlayerCallback<H>> playerCallbacks = new CopyOnWriteArrayList<>();
    final List<ConnectionListener> connectionListeners = new CopyOnWriteArrayList<>();

    private final Logger log = LoggerFactory.getLogger(PlayerCallbackAdapter.class);

    private final PacketReader reader;
    private final Map<Class, PacketHandler> handlers = new HashMap<>();
    private final AtomicBoolean continueReading = new AtomicBoolean(true);

    PlayerCallbackAdapter(final ConnectorBuilder<H> connector,
                          final PacketReader reader) {
        this.reader = reader;

        handlers.put(PacketCardPlaced.class, new CardPlacedHandler(boardCallbacks));
        handlers.put(PacketCardRemoved.class, new CardRemovedHandler(boardCallbacks));
        handlers.put(PacketNewTurn.class, new NewTurnHandler(gameEventListeners));
        handlers.put(PacketGameOver.class, new GameOverHandler(gameEventListeners, continueReading));
        handlers.put(PacketReceiveCard.class, new ReceiveCardHandler<>(playerCallbacks, connector.hints));
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

}
