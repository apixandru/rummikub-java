package com.github.apixandru.games.rummikub.client;

import com.github.apixandru.games.rummikub.brotocol.IntWriter;
import com.github.apixandru.games.rummikub.brotocol.util.AbstractIntWritable;
import com.github.apixandru.games.rummikub.model.Card;
import com.github.apixandru.games.rummikub.model.Player;

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
final class SocketPlayer<H> extends AbstractIntWritable implements Player<H> {

    private final List<H> hints;

    /**
     * @param writer
     * @param cards
     * @param hints
     * @throws IOException
     */
    SocketPlayer(final IntWriter writer, final List<Card> cards, final List<H> hints) throws IOException {
        super(writer, cards);
        this.hints = hints;
    }

    @Override
    public void placeCardOnBoard(final Card card, final int x, final int y) {
        write(CLIENT_PLACE_CARD, card, x, y);
    }

    @Override
    public void moveCardOnBoard(final Card card, final int fromX, final int fromY, final int toX, final int toY) {
        write(CLIENT_MOVE_CARD, card, fromX, fromY, toX, toY);
    }

    @Override
    public void takeCardFromBoard(final Card card, final int x, final int y, final H hint) {
        write(CLIENT_TAKE_CARD, card, x, y, this.hints.indexOf(hint));
    }

    @Override
    public void endTurn() {
        write(CLIENT_END_TURN);
        flush();
    }

    @Override
    public boolean canMoveCardOffBoard(final Card card) {
        return true;
    }

}
