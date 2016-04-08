package com.apixandru.games.rummikub.server;

import com.apixandru.games.rummikub.brotocol.Packet;
import com.apixandru.games.rummikub.brotocol.PacketHandler;
import com.apixandru.games.rummikub.brotocol.PacketReader;
import com.apixandru.games.rummikub.brotocol.connect.client.PacketLeave;
import com.apixandru.games.rummikub.brotocol.game.client.PacketEndTurn;
import com.apixandru.games.rummikub.brotocol.game.client.PacketMoveCard;
import com.apixandru.games.rummikub.brotocol.game.client.PacketPlaceCard;
import com.apixandru.games.rummikub.brotocol.game.client.PacketTakeCard;
import com.apixandru.games.rummikub.model.Rummikub;
import com.apixandru.games.rummikub.server.game.EndTurnHandler;
import com.apixandru.games.rummikub.server.game.MoveCardHandler;
import com.apixandru.games.rummikub.server.game.PlaceCardOnBoardHandler;
import com.apixandru.games.rummikub.server.game.TakeCardHandler;
import com.apixandru.games.rummikub.server.waiting.LeaveHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 04, 2016
 */
final class ClientRunnable implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(ClientRunnable.class);

    private final Map<Class, PacketHandler> handlers;

    private final PacketReader reader;
    private final PlayerProvider<Integer> playerProvider;
    private final Rummikub<Integer> game;

    private final AtomicBoolean continueReading = new AtomicBoolean(true);

    ClientRunnable(final PacketReader reader,
                   final PlayerProvider<Integer> playerProvider,
                   final Rummikub<Integer> game) {
        this.reader = reader;
        this.playerProvider = playerProvider;
        this.game = game;

        this.handlers = createHandlers();
    }

    private Map<Class, PacketHandler> createHandlers() {
        final Map<Class, PacketHandler> gameHandlers = new HashMap<>();
        gameHandlers.put(PacketPlaceCard.class, new PlaceCardOnBoardHandler(playerProvider));
        gameHandlers.put(PacketEndTurn.class, new EndTurnHandler(playerProvider));
        gameHandlers.put(PacketMoveCard.class, new MoveCardHandler(playerProvider));
        gameHandlers.put(PacketTakeCard.class, new TakeCardHandler(playerProvider));
        gameHandlers.put(PacketLeave.class, new LeaveHandler(continueReading));
        return Collections.unmodifiableMap(gameHandlers);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void run() {
        try (final Closeable automaticallyCloseMe = this.reader) {
            while (continueReading.get()) {
                final Packet input = reader.readPacket();
                final PacketHandler packetHandler = handlers.get(input.getClass());
                packetHandler.handle(input);
            }
        } catch (final EOFException e) {
            log.debug("{} quit the game", playerProvider.getPlayer().getName());
        } catch (IOException e) {
            log.error("Failed to read packet", e);
        }
        this.game.removePlayer(this.playerProvider.getPlayer());
    }

}
