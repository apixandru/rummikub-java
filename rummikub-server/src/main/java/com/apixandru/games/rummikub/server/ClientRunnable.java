package com.apixandru.games.rummikub.server;

import com.apixandru.games.rummikub.api.Card;
import com.apixandru.games.rummikub.api.Player;
import com.apixandru.games.rummikub.brotocol.Packet;
import com.apixandru.games.rummikub.brotocol.PacketHandler;
import com.apixandru.games.rummikub.brotocol.PacketReader;
import com.apixandru.games.rummikub.brotocol.connect.client.PacketJoin;
import com.apixandru.games.rummikub.brotocol.connect.client.PacketReady;
import com.apixandru.games.rummikub.brotocol.game.client.PacketEndTurn;
import com.apixandru.games.rummikub.brotocol.game.client.PacketMoveCard;
import com.apixandru.games.rummikub.brotocol.game.client.PacketPlaceCard;
import com.apixandru.games.rummikub.brotocol.game.client.PacketTakeCard;
import com.apixandru.games.rummikub.model.Rummikub;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 04, 2016
 */
final class ClientRunnable implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(ClientRunnable.class);

    private final Map<Class, PacketHandler> waitingRoomHandlers;

    private final Map<Class, PacketHandler> gameHandlers;

    private final PacketReader reader;
    private final Player<Integer> player;
    private final Rummikub<Integer> game;
    private final String playerName;

    ClientRunnable(final PacketReader reader,
                   final Player<Integer> player,
                   final Rummikub<Integer> game) {
        this.reader = reader;
        this.player = player;
        this.playerName = player.getName();
        this.game = game;

        this.gameHandlers = createGameHandlers();
        this.waitingRoomHandlers = createWaitingRoomHandlers();
    }

    private Map<Class, PacketHandler> createGameHandlers() {
        final Map<Class, PacketHandler> gameHandlers = new HashMap<>();
        gameHandlers.put(PacketPlaceCard.class, new PlaceCardOnBoardHandler());
        gameHandlers.put(PacketEndTurn.class, new EndTurnHandler());
        gameHandlers.put(PacketMoveCard.class, new MoveCardHandler());
        gameHandlers.put(PacketTakeCard.class, new TakeCardHandler());
        return Collections.unmodifiableMap(gameHandlers);
    }

    private Map<Class, PacketHandler> createWaitingRoomHandlers() {
        final Map<Class, PacketHandler> handlers = new HashMap<>();
        handlers.put(PacketJoin.class, new JoinHandler());
        handlers.put(PacketReady.class, new ReadyHandler());
        return Collections.unmodifiableMap(handlers);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void run() {
        try (final Closeable automaticallyCloseMe = this.reader) {
            while (true) {
                final Packet input = reader.readPacket();
                final PacketHandler packetHandler = gameHandlers.get(input.getClass());
                packetHandler.handle(input);
            }
        } catch (final EOFException e) {
            log.debug("{} quit the game", playerName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.game.removePlayer(this.player);
    }

    private class PlaceCardOnBoardHandler implements PacketHandler<PacketPlaceCard> {

        @Override
        public void handle(final PacketPlaceCard packet) {
            final Card card = packet.card;
            final int x = packet.x;
            final int y = packet.y;
            log.debug("[{}] Received placeCardOnBoard(card={}, x={}, y={})", playerName, card, x, y);
            player.placeCardOnBoard(card, x, y);
        }
    }

    private class EndTurnHandler implements PacketHandler<PacketEndTurn> {

        @Override
        public void handle(final PacketEndTurn packet) {
            log.debug("[{}] Received endTurn()", playerName);
            player.endTurn();
        }
    }

    private class MoveCardHandler implements PacketHandler<PacketMoveCard> {

        @Override
        public void handle(final PacketMoveCard packet) {
            final Card card = packet.card;
            final int fromX = packet.fromX;
            final int fromY = packet.fromY;
            final int toX = packet.toX;
            final int toY = packet.toY;
            log.debug("[{}] Received moveCardOnBoard(card={}, fromX={}, fromY={}, toX={}, toY={})", playerName, card, fromX, fromY, toX, toY);
            player.moveCardOnBoard(card, fromX, fromY, toX, toY);
        }
    }

    private class TakeCardHandler implements PacketHandler<PacketTakeCard> {

        @Override
        public void handle(final PacketTakeCard packet) {
            final Card card = packet.card;
            final int x = packet.x;
            final int y = packet.y;
            final int hint = packet.hint;
            log.debug("[{}] Received takeCardFromBoard(card={}, x={}, y={}, hint={})", playerName, card, x, y, hint);
            player.takeCardFromBoard(card, x, y, hint);
        }
    }

    private class JoinHandler implements PacketHandler<PacketJoin> {

        @Override
        public void handle(final PacketJoin packet) {

        }

    }


    private class ReadyHandler implements PacketHandler<PacketReady> {

        @Override
        public void handle(final PacketReady packet) {

        }

    }

}
