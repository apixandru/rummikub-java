package com.github.apixandru.games.rummikub.client;

import com.github.apixandru.games.rummikub.brotocol.IntReader;
import com.github.apixandru.games.rummikub.model.Card;
import com.github.apixandru.games.rummikub.model.PlayerCallback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.github.apixandru.games.rummikub.brotocol.Brotocol.SERVER_CARD_PLACED;
import static com.github.apixandru.games.rummikub.brotocol.Brotocol.SERVER_CARD_REMOVED;
import static com.github.apixandru.games.rummikub.brotocol.Brotocol.SERVER_RECEIVED_CARD;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 04, 2016
 */
final class PlayerCallbackAdapter<H> implements Runnable {

    private final IntReader intReader;
    private final PlayerCallback<H> callback;
    private final List<Card> cards = new ArrayList<>();
    private final List<H> hints;

    /**
     * @param reader
     * @param callback
     * @param hints
     */
    public PlayerCallbackAdapter(final IntReader reader,
                                 final PlayerCallback<H> callback,
                                 final List<H> hints) {
        this.intReader = reader;
        this.callback = callback;
        this.hints = Collections.unmodifiableList(hints);
    }

    @Override
    public void run() {
        try (IntReader reader = intReader) {
            while (true) {
                final int input = reader.readInt();
                final Card card = this.cards.get(reader.readInt());
                switch (input) {
                    case SERVER_CARD_PLACED:
                        handleCardPlaced(card, reader);
                        break;
                    case SERVER_CARD_REMOVED:
                        handleCardRemoved(card, reader);
                        break;
                    case SERVER_RECEIVED_CARD:
                        handleReceivedCard(card, reader);
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
     * @param card
     * @param reader
     * @throws IOException
     */
    private void handleReceivedCard(final Card card, final IntReader reader) throws IOException {
        final int hintIndex = reader.readInt();
        this.callback.cardReceived(card, this.hints.get(hintIndex));
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
