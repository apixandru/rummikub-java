package com.apixandru.games.rummikub.ui;

import com.apixandru.games.rummikub.api.Card;
import com.apixandru.games.rummikub.api.PlayerCallback;

import javax.swing.*;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 04, 2016
 */
final class CardSlotCallback implements PlayerCallback<CardSlot>, TurnIndicator {

    private final BoardUi board;
    private final PlayerUi player;
    private final JButton btnEndTurn;

    private AtomicBoolean myTurn = new AtomicBoolean();

    /**
     * @param board
     * @param player
     * @param btnEndTurn
     */
    public CardSlotCallback(final BoardUi board, final PlayerUi player, final JButton btnEndTurn) {
        this.board = board;
        this.player = player;
        this.btnEndTurn = btnEndTurn;
        newTurn(false);
    }

    @Override
    public void cardReceived(final Card card, final CardSlot hint) {
        UiUtil.placeCard(card, player.orFirstFreeSlot(hint));
    }

    @Override
    public void onCardPlacedOnBoard(final Card card, final int x, final int y) {
        UiUtil.placeCard(new CardUi(card), board.slots[y][x]);
    }

    @Override
    public void onCardRemovedFromBoard(final Card card, final int x, final int y) {
        UiUtil.removeCard(board.slots[y][x]);
    }

    @Override
    public void newTurn(final boolean myTurn) {
        this.btnEndTurn.setEnabled(myTurn);
        this.myTurn.set(myTurn);
    }

    @Override
    public boolean isMyTurn() {
        return this.myTurn.get();
    }

}
