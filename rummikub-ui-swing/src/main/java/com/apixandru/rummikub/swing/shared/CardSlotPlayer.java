package com.apixandru.rummikub.swing.shared;

import com.apixandru.rummikub.api.Card;
import com.apixandru.rummikub.api.Player;

/**
 * @author Alexandru-Constantin Bledea
 * @since Apr 21, 2016
 */
public final class CardSlotPlayer implements Player<CardSlot> {

    private final CardSlotIndexConverter converter;
    private final Player<Integer> player;

    public CardSlotPlayer(final Player<Integer> player, final CardSlotIndexConverter converter) {
        this.player = player;
        this.converter = converter;
    }

    @Override
    public void placeCardOnBoard(final Card card, final int x, final int y) {
        player.placeCardOnBoard(card, x, y);
    }

    @Override
    public void moveCardOnBoard(final int fromX, final int fromY, final int toX, final int toY) {
        player.moveCardOnBoard(fromX, fromY, toX, toY);
    }

    @Override
    public void takeCardFromBoard(final Card card, final int x, final int y, final CardSlot hint) {
        player.takeCardFromBoard(card, x, y, converter.getIndex(hint));
    }

    @Override
    public void endTurn() {
        player.endTurn();
    }

}