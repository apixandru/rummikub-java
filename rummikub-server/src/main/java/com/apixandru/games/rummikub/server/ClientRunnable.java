package com.apixandru.games.rummikub.server;

import com.apixandru.games.rummikub.api.Card;
import com.apixandru.games.rummikub.api.Player;
import com.apixandru.games.rummikub.brotocol.BroReader;
import com.apixandru.games.rummikub.brotocol.PacketHandler;
import com.apixandru.games.rummikub.brotocol.client.PacketEndTurn;
import com.apixandru.games.rummikub.brotocol.client.PacketMoveCard;
import com.apixandru.games.rummikub.brotocol.client.PacketPlaceCard;
import com.apixandru.games.rummikub.brotocol.client.PacketTakeCard;
import com.apixandru.games.rummikub.model.Rummikub;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.EOFException;
import java.io.IOException;
import java.util.List;

import static com.apixandru.games.rummikub.brotocol.Brotocol.*;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 04, 2016
 */
final class ClientRunnable implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(ClientRunnable.class);

    private final BroReader reader;
    private final List<Card> cards;
    private final Player<Integer> player;
    private final Rummikub<Integer> game;
    private final String playerName;

    /**
     * @param reader the reader
     * @param cards  the list of all the cards in the game
     * @param player the current player
     * @param game   the game
     */
    ClientRunnable(final BroReader reader,
                   final List<Card> cards,
                   final Player<Integer> player,
                   final Rummikub<Integer> game) {
        this.cards = cards;
        this.reader = reader;
        this.player = player;
        this.playerName = player.getName();
        this.game = game;
    }

    @Override
    public void run() {
        try (final BroReader reader = this.reader) {
            while (true) {
                final int input = reader.readInt();
                switch (input) {
                    case CLIENT_PLACE_CARD:
                        handlePlaceCardOnBoard();
                        break;
                    case CLIENT_END_TURN:
                        handleEndTurn();
                        break;
                    case CLIENT_TAKE_CARD:
                        handleTakeCardFromBoard();
                        break;
                    case CLIENT_MOVE_CARD:
                        handleMoveCardOnBoard();
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown input: " + input);
                }
            }
        } catch (final EOFException e) {
            log.debug("{} quit the game", playerName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.game.removePlayer(this.player);
    }

    /**
     * @throws IOException
     */
    private void handleMoveCardOnBoard() throws IOException {
        final Card card = readCard();
        final int fromX = reader.readInt();
        final int fromY = reader.readInt();
        final int toX = reader.readInt();
        final int toY = reader.readInt();
        log.debug("[{}] Received moveCardOnBoard(card={}, fromX={}, fromY={}, toX={}, toY={})", playerName, card, fromX, fromY, toX, toY);
        player.moveCardOnBoard(card, fromX, fromY, toX, toY);
    }

    /**
     * @throws IOException
     */
    private void handleTakeCardFromBoard() throws IOException {
        final Card card = readCard();
        final int x = reader.readInt();
        final int y = reader.readInt();
        final int hint = reader.readInt();
        log.debug("[{}] Received takeCardFromBoard(card={}, x={}, y={}, hint={})", playerName, card, x, y, hint);
        player.takeCardFromBoard(card, x, y, hint);
    }

    /**
     *
     */
    private void handleEndTurn() {
        log.debug("[{}] Received endTurn()", playerName);
        player.endTurn();
    }

    /**
     * @throws IOException
     */
    private void handlePlaceCardOnBoard() throws IOException {
        final Card card = readCard();
        final int x = reader.readInt();
        final int y = reader.readInt();
        log.debug("[{}] Received placeCardOnBoard(card={}, x={}, y={})", playerName, card, x, y);
        player.placeCardOnBoard(card, x, y);
    }

    /**
     * @return the card identified by the position in the list of cards
     * @throws IOException
     */
    private Card readCard() throws IOException {
        return this.cards.get(reader.readInt());
    }

    private class PlaceCardOnBoardHandler implements PacketHandler<PacketPlaceCard> {

        @Override
        public <H> void handle(final PacketPlaceCard packet) {
            final Card card = packet.card;
            final int x = packet.x;
            final int y = packet.y;
            log.debug("[{}] Received placeCardOnBoard(card={}, x={}, y={})", playerName, card, x, y);
            player.placeCardOnBoard(card, x, y);
        }
    }

    private class EndTurnHandler implements PacketHandler<PacketEndTurn> {

        @Override
        public <H> void handle(final PacketEndTurn packet) {
            log.debug("[{}] Received endTurn()", playerName);
            player.endTurn();
        }
    }

    private class MoveCardHandler implements PacketHandler<PacketMoveCard> {

        @Override
        public <H> void handle(final PacketMoveCard packet) {
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
        public <H> void handle(final PacketTakeCard packet) {
            final Card card = packet.card;
            final int x = packet.x;
            final int y = packet.y;
            final int hint = packet.hint;
            log.debug("[{}] Received takeCardFromBoard(card={}, x={}, y={}, hint={})", playerName, card, x, y, hint);
            player.takeCardFromBoard(card, x, y, hint);
        }
    }

}
