package com.github.apixandru.games.rummikub.server;

import com.github.apixandru.games.rummikub.brotocol.IntReader;
import com.github.apixandru.games.rummikub.brotocol.IntWriter;
import com.github.apixandru.games.rummikub.brotocol.util.AbstractIntWritable;
import com.github.apixandru.games.rummikub.model.Card;
import com.github.apixandru.games.rummikub.model.Player;
import com.github.apixandru.games.rummikub.model.PlayerCallback;

import java.io.IOException;
import java.util.List;

import static com.github.apixandru.games.rummikub.brotocol.Brotocol.SERVER_CARD_PLACED;
import static com.github.apixandru.games.rummikub.brotocol.Brotocol.SERVER_CARD_REMOVED;
import static com.github.apixandru.games.rummikub.brotocol.Brotocol.SERVER_RECEIVED_CARD;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 04, 2016
 */
public class ClientRunnable extends AbstractIntWritable implements Runnable, PlayerCallback<Integer>, Player<Integer> {

    private final IntReader intReader;

    /**
     * @param reader
     * @param writer
     * @param cards
     * @throws IOException
     */
    ClientRunnable(final IntReader reader, final IntWriter writer, final List<Card> cards) throws IOException {
        super(writer, cards);
        this.intReader = reader;
    }

    @Override
    public void run() {
        try (IntReader reader = intReader) {
            while (true) {
                final int input = reader.readInt();
                System.out.println(input);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void cardReceived(final Card card, final Integer hint) {
        write(SERVER_RECEIVED_CARD, card, hint == null ? -1 : hint);
    }

    @Override
    public void onCardPlacedOnBoard(final Card card, final int x, final int y) {
        write(SERVER_CARD_PLACED, card, x, y);
    }

    @Override
    public void onCardRemovedFromBoard(final Card card, final int x, final int y) {
        write(SERVER_CARD_REMOVED, card, x, y);
    }

    @Override
    public void placeCardOnBoard(final Card card, final int x, final int y) {

    }

    @Override
    public void moveCardOnBoard(final Card card, final int fromX, final int fromY, final int toX, final int toY) {

    }

    @Override
    public void takeCardFromBoard(final Card card, final int x, final int y, final Integer hint) {

    }

    @Override
    public void endTurn() {

    }

    @Override
    public boolean canMoveCardOffBoard(final Card card) {
        return false;
    }
}
