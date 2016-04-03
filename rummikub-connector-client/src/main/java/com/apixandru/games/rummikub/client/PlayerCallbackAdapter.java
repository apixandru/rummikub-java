package com.apixandru.games.rummikub.client;

import com.apixandru.games.rummikub.api.BoardCallback;
import com.apixandru.games.rummikub.api.GameEventListener;
import com.apixandru.games.rummikub.api.GameOverReason;
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
import com.apixandru.games.rummikub.client.game.NewTurnHandler;
import com.apixandru.games.rummikub.client.game.ReceiveCardHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static java.util.Collections.singleton;

/**
 * @param <H> hint type
 * @author Alexandru-Constantin Bledea
 * @since January 04, 2016
 */
final class PlayerCallbackAdapter<H> implements Runnable {

    private final Logger log = LoggerFactory.getLogger(PlayerCallbackAdapter.class);

    private final PacketReader reader;

    private final Map<Class, PacketHandler> handlers = new HashMap<>();

    private final PlayerCallback<H> playerCallback;
    private final GameEventListener gameEventListener;

    private final ConnectionListener connectionListener;

    private boolean continueReading = true;

    PlayerCallbackAdapter(final ConnectorBuilder<H> connector,
                          final PacketReader reader) {
        this.reader = reader;

        this.playerCallback = connector.callback;
        BoardCallback boardCallback = connector.boardCallback;
        this.gameEventListener = connector.gameEventListener;

        this.connectionListener = connector.connectionListener;

        handlers.put(PacketCardPlaced.class, new CardPlacedHandler(boardCallback));
        handlers.put(PacketCardRemoved.class, new CardRemovedHandler(boardCallback));
        handlers.put(PacketNewTurn.class, new NewTurnHandler(singleton(gameEventListener)));
        handlers.put(PacketGameOver.class, new GameOverHandler());
        handlers.put(PacketReceiveCard.class, new ReceiveCardHandler<>(playerCallback, connector.hints));
    }

    @Override
    @SuppressWarnings("unchecked")
    public void run() {
        try (final Closeable closeMe = this.reader) {
            while (continueReading) {
                final Packet input = reader.readPacket();
                final PacketHandler packetHandler = handlers.get(input.getClass());
                packetHandler.handle(input);
            }
        } catch (final EOFException e) {
            log.debug("Server was shutdown?", e);
            this.connectionListener.onConnectionLost();
        } catch (final IOException e) {
            log.error("Unknown exception", e);
            this.connectionListener.onConnectionLost();
        }
    }

    private class GameOverHandler implements PacketHandler<PacketGameOver> {

        @Override
        public void handle(final PacketGameOver packet) {
            final String player = packet.player;
            final GameOverReason reason = packet.reason;
            final boolean me = packet.me;
            log.debug("Received gameOver(player={}, reason={}, me={})", player, reason, me);
            gameEventListener.gameOver(player, reason, me);

            continueReading = false;
        }
    }

}
