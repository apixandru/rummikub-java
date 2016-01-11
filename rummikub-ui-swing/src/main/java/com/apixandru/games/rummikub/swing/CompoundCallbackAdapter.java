package com.apixandru.games.rummikub.swing;

import com.apixandru.games.rummikub.api.BoardCallback;
import com.apixandru.games.rummikub.api.Card;
import com.apixandru.games.rummikub.api.CompoundCallback;
import com.apixandru.games.rummikub.api.GameEventListener;
import com.apixandru.games.rummikub.api.PlayerCallback;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 11, 2016
 */
final class CompoundCallbackAdapter implements CompoundCallback<CardSlot> {

    private final PlayerCallback<CardSlot> playerCallback;
    private final BoardCallback boardCallback;
    private final GameEventListener gameEventListener;

    /**
     * @param playerCallback
     * @param boardCallback
     * @param gameEventListener
     */
    public CompoundCallbackAdapter(final PlayerCallback<CardSlot> playerCallback, final BoardCallback boardCallback, final GameEventListener gameEventListener) {
        this.playerCallback = playerCallback;
        this.boardCallback = boardCallback;
        this.gameEventListener = gameEventListener;
    }

    @Override
    public void onCardPlacedOnBoard(final Card card, final int x, final int y) {
        this.boardCallback.onCardPlacedOnBoard(card, x, y);
    }

    @Override
    public void onCardRemovedFromBoard(final Card card, final int x, final int y, final boolean reset) {
        this.boardCallback.onCardRemovedFromBoard(card, x, y, reset);
    }

    @Override
    public void newTurn(final boolean myTurn) {
        this.gameEventListener.newTurn(myTurn);
    }

    @Override
    public void gameOver(final String player, final boolean quit, final boolean me) {
        this.gameEventListener.gameOver(player, quit, me);
    }

    @Override
    public void cardReceived(final Card card, final CardSlot hint) {
        this.playerCallback.cardReceived(card, hint);
    }

}
