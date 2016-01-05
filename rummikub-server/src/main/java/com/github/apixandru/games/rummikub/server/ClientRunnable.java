package com.github.apixandru.games.rummikub.server;

import com.github.apixandru.games.rummikub.brotocol.IntReader;
import com.github.apixandru.games.rummikub.api.Card;
import com.github.apixandru.games.rummikub.api.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

import static com.github.apixandru.games.rummikub.brotocol.Brotocol.CLIENT_END_TURN;
import static com.github.apixandru.games.rummikub.brotocol.Brotocol.CLIENT_MOVE_CARD;
import static com.github.apixandru.games.rummikub.brotocol.Brotocol.CLIENT_PLACE_CARD;
import static com.github.apixandru.games.rummikub.brotocol.Brotocol.CLIENT_TAKE_CARD;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 04, 2016
 */
final class ClientRunnable implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(ClientRunnable.class);

    private final IntReader intReader;
    private final List<Card> cards;
    private final Player<Integer> player;

    /**
     * @param reader
     * @param cards
     * @param player
     * @throws IOException
     */
    ClientRunnable(final IntReader reader, final List<Card> cards, final Player<Integer> player) throws IOException {
        this.cards = cards;
        this.intReader = reader;
        this.player = player;
    }

    @Override
    public void run() {
        try (IntReader reader = intReader) {
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param reader
     * @throws IOException
     */
    private void handleMoveCardOnBoard(final IntReader reader) throws IOException {
        log.info("Received on moveCardOnBoard request.");
        final Card card = readCard(reader);
        final int fromX = reader.readInt();
        final int fromY = reader.readInt();
        final int toX = reader.readInt();
        final int toY = reader.readInt();
        log.info("Params: Card={}, fromX={}, fromY={}, toX={}, toY={}", card, fromX, fromY, toX, toY);
        player.moveCardOnBoard(card, fromX, fromY, toX, toY);
    }

    /**
     * @param reader
     * @throws IOException
     */
    private void handleTakeCardFromBoard(final IntReader reader) throws IOException {
        log.info("Received on takeCardFromBoard request.");
        final Card card = readCard(reader);
        final int x = reader.readInt();
        final int y = reader.readInt();
        final int hint = reader.readInt();
        log.info("Params: Card={}, x={}, y={}, hint={}", card, x, y, hint);
        player.takeCardFromBoard(card, x, y, hint);
    }

    /**
     *
     */
    private void handleEndTurn() {
        log.info("Received on endTurn request.");
        player.endTurn();
    }

    /**
     * @param reader
     * @throws IOException
     */
    private void handlePlaceCardOnBoard(final IntReader reader) throws IOException {
        log.info("Received on placeCardOnBoard request.");
        final Card card = readCard(reader);
        final int x = reader.readInt();
        final int y = reader.readInt();
        log.info("Params: Card={}, x={}, y={}", card, x, y);
        player.placeCardOnBoard(card, x, y);
    }

    /**
     * @param reader
     * @return
     * @throws IOException
     */
    private Card readCard(final IntReader reader) throws IOException {
        return this.cards.get(reader.readInt());
    }

}
