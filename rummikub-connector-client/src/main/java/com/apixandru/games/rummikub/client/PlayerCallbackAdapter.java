package com.apixandru.games.rummikub.client;

import com.apixandru.games.rummikub.api.Card;
import com.apixandru.games.rummikub.api.PlayerCallback;
import com.apixandru.games.rummikub.brotocol.IntReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.apixandru.games.rummikub.brotocol.Brotocol.SERVER_CARD_PLACED;
import static com.apixandru.games.rummikub.brotocol.Brotocol.SERVER_CARD_REMOVED;
import static com.apixandru.games.rummikub.brotocol.Brotocol.SERVER_NEW_TURN;
import static com.apixandru.games.rummikub.brotocol.Brotocol.SERVER_RECEIVED_CARD;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 04, 2016
 */
final class PlayerCallbackAdapter<H> implements Runnable {

    private final Logger log = LoggerFactory.getLogger(PlayerCallbackAdapter.class);

    private final IntReader intReader;
    private final PlayerCallback<H> callback;
    private final List<Card> cards;
    private final List<H> hints;
    private final ConnectionListener connectionListener;

    /**
     * @param connector
     * @param reader
     * @param cards
     */
    PlayerCallbackAdapter(final RummikubConnector<H> connector,
                          final IntReader reader,
                          final List<Card> cards) {
        this.intReader = reader;
        this.callback = connector.callback;
        this.connectionListener = connector.connectionListener;
        this.cards = Collections.unmodifiableList(new ArrayList<>(cards));
        this.hints = Collections.unmodifiableList(new ArrayList<H>(connector.hints));
    }


    @Override
    public void run() {
        try (final IntReader reader = intReader) {
            while (true) {
                final int input = reader.readInt();
                switch (input) {
                    case SERVER_CARD_PLACED:
                        handleCardPlaced(getCard(reader), reader);
                        break;
                    case SERVER_CARD_REMOVED:
                        handleCardRemoved(getCard(reader), reader);
                        break;
                    case SERVER_RECEIVED_CARD:
                        handleReceivedCard(getCard(reader), reader);
                        break;
                    case SERVER_NEW_TURN:
                        handleNewTurn(reader);
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown input: " + input);
                }
            }
        } catch (final EOFException e) {
            log.debug("Server was shutdown?", e);
        } catch (final IOException e) {
            log.error("Unknown exception", e);
        }
        this.connectionListener.onDisconnected();
    }

    private Card getCard(final IntReader reader) throws IOException {
        return this.cards.get(reader.readInt());
    }

    /**
     * @param reader
     * @throws IOException
     */
    private void handleNewTurn(final IntReader reader) throws IOException {
        final boolean myTurn = reader.readInt() == 1;
        this.callback.newTurn(myTurn);
    }

    /**
     * @param card
     * @param reader
     * @throws IOException
     */
    private void handleReceivedCard(final Card card, final IntReader reader) throws IOException {
        final int hintIndex = reader.readInt();
        this.callback.cardReceived(card, -1 == hintIndex ? null : this.hints.get(hintIndex));
    }

    /**
     * @param card
     * @param reader
     * @throws IOException
     */
    private void handleCardRemoved(final Card card, final IntReader reader) throws IOException {
        final int x = reader.readInt();
        final int y = reader.readInt();
        this.callback.onCardRemovedFromBoard(card, x, y);
    }

    /**
     * @param card
     * @param reader
     * @throws IOException
     */
    private void handleCardPlaced(final Card card, final IntReader reader) throws IOException {
        final int x = reader.readInt();
        final int y = reader.readInt();
        this.callback.onCardPlacedOnBoard(card, x, y);
    }

}
