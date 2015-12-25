package com.github.apixandru.games.rummikub.model2;

import com.github.apixandru.games.rummikub.model.Card;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 25, 2015
 */
class PlayerImpl implements Player {

    private final PlayerListener listener;

    final List<Card> cards = new ArrayList<>();

    /**
     * @param listener
     */
    PlayerImpl(final PlayerListener listener) {
        this.listener = listener;
    }

    @Override
    public boolean placeCardOnBoard(final Card card, final int x, final int y) {
        return this.listener.placeCardOnBoard(this, card, x, y);
    }

    @Override
    public boolean moveCardOnBoard(final Card card, final int fromX, final int fromY, final int toX, final int toY) {
        this.listener.moveCardOnBoard(this, card, fromX, fromY, toX, toY);
        return false;
    }

    @Override
    public void endTurn() {
        this.listener.requestEndTurn(this);
    }

    /**
     * @param card
     */
    public void receiveCard(final Card card) {
        this.cards.add(card);
    }

}
