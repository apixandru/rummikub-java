package com.apixandru.games.rummikub.ui;

import com.apixandru.games.rummikub.api.Card;
import com.apixandru.games.rummikub.api.PlayerCallback;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 04, 2016
 */
final class CardSlotCallback implements PlayerCallback<CardSlot> {

    private final BoardUi board;
    private final PlayerUi player;

    public CardSlotCallback(final BoardUi board, final PlayerUi player) {
        this.board = board;
        this.player = player;
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

}
