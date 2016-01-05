package com.github.apixandru.games.rummikub.server;

import com.github.apixandru.games.rummikub.brotocol.IntWriter;
import com.github.apixandru.games.rummikub.brotocol.util.AbstractIntWritable;
import com.github.apixandru.games.rummikub.model.Card;
import com.github.apixandru.games.rummikub.model.PlayerCallback;

import java.io.IOException;
import java.util.List;

import static com.github.apixandru.games.rummikub.brotocol.Brotocol.SERVER_CARD_PLACED;
import static com.github.apixandru.games.rummikub.brotocol.Brotocol.SERVER_CARD_REMOVED;
import static com.github.apixandru.games.rummikub.brotocol.Brotocol.SERVER_RECEIVED_CARD;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 05, 2016
 */
final class ClientCallback extends AbstractIntWritable implements PlayerCallback<Integer> {

    /**
     * @param writer
     * @param cards
     * @throws IOException
     */
    ClientCallback(final IntWriter writer, final List<Card> cards) throws IOException {
        super(writer, cards);
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

}
