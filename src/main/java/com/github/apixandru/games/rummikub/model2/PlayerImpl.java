package com.github.apixandru.games.rummikub.model2;

import com.github.apixandru.games.rummikub.model.Card;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 25, 2015
 */
class PlayerImpl implements Player {

    private final PlayerListener listener;

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
    public void endTurn() {
        this.listener.requestEndTurn(this);
    }

    public void receiveCard(final Card card) {

    }

}
