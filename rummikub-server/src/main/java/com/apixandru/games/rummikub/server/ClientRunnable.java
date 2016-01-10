package com.apixandru.games.rummikub.server;

import com.apixandru.games.rummikub.api.Card;
import com.apixandru.games.rummikub.api.Player;
import com.apixandru.games.rummikub.brotocol.BroReader;
import com.apixandru.games.rummikub.model.Rummikub;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.EOFException;
import java.io.IOException;
import java.util.List;

import static com.apixandru.games.rummikub.brotocol.Brotocol.CLIENT_END_TURN;
import static com.apixandru.games.rummikub.brotocol.Brotocol.CLIENT_MOVE_CARD;
import static com.apixandru.games.rummikub.brotocol.Brotocol.CLIENT_PLACE_CARD;
import static com.apixandru.games.rummikub.brotocol.Brotocol.CLIENT_TAKE_CARD;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 04, 2016
 */
final class ClientRunnable implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(ClientRunnable.class);

    private final BroReader broReader;
    private final List<Card> cards;
    private final Player<Integer> player;
    private final Rummikub<Integer> game;

    /**
     * @param reader
     * @param cards
     * @param player
     * @param game
     */
    ClientRunnable(final BroReader reader,
                   final List<Card> cards,
                   final Player<Integer> player,
                   final Rummikub<Integer> game) {
        this.cards = cards;
        this.broReader = reader;
        this.player = player;
        this.game = game;
    }

    @Override
    public void run() {
        try (BroReader reader = broReader) {
            while (true) {
                final int input = reader.readInt();
                switch (input) {
                    case CLIENT_PLACE_CARD:
                        handlePlaceCardOnBoard(reader);
                        break;
                    case CLIENT_END_TURN:
                        handleEndTurn();
                        break;
                    case CLIENT_TAKE_CARD:
                        handleTakeCardFromBoard(reader);
                        break;
                    case CLIENT_MOVE_CARD:
                        handleMoveCardOnBoard(reader);
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown input: " + input);
                }
            }
        } catch (final EOFException e) {
            log.debug("Player quit the game");
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.game.removePlayer(this.player);
    }

    /**
     * @param reader
     * @throws IOException
     */
    private void handleMoveCardOnBoard(final BroReader reader) throws IOException {
        log.debug("Received on moveCardOnBoard request.");
        final Card card = readCard(reader);
        final int fromX = reader.readInt();
        final int fromY = reader.readInt();
        final int toX = reader.readInt();
        final int toY = reader.readInt();
        log.debug("Params: Card={}, fromX={}, fromY={}, toX={}, toY={}", card, fromX, fromY, toX, toY);
        player.moveCardOnBoard(card, fromX, fromY, toX, toY);
    }

    /**
     * @param reader
     * @throws IOException
     */
    private void handleTakeCardFromBoard(final BroReader reader) throws IOException {
        log.debug("Received on takeCardFromBoard request.");
        final Card card = readCard(reader);
        final int x = reader.readInt();
        final int y = reader.readInt();
        final int hint = reader.readInt();
        log.debug("Params: Card={}, x={}, y={}, hint={}", card, x, y, hint);
        player.takeCardFromBoard(card, x, y, hint);
    }

    /**
     *
     */
    private void handleEndTurn() {
        log.debug("Received on endTurn request.");
        player.endTurn();
    }

    /**
     * @param reader
     * @throws IOException
     */
    private void handlePlaceCardOnBoard(final BroReader reader) throws IOException {
        log.debug("Received on placeCardOnBoard request.");
        final Card card = readCard(reader);
        final int x = reader.readInt();
        final int y = reader.readInt();
        log.debug("Params: Card={}, x={}, y={}", card, x, y);
        player.placeCardOnBoard(card, x, y);
    }

    /**
     * @param reader
     * @return
     * @throws IOException
     */
    private Card readCard(final BroReader reader) throws IOException {
        return this.cards.get(reader.readInt());
    }

}
