package com.apixandru.rummikub.swing;

import com.apixandru.rummikub.api.Card;
import com.apixandru.rummikub.api.Player;

import java.util.List;

/**
 * @author Alexandru-Constantin Bledea
 * @since Apr 21, 2016
 */
public class CardSlotPlayer implements Player<CardSlot> {

    private final Player<Integer> player;
    private final List<CardSlot> cardSlots;

    public CardSlotPlayer(final Player<Integer> player, final List<CardSlot> cardSlots) {
        this.player = player;
        this.cardSlots = cardSlots;
    }

    @Override
    public String getName() {
        return player.getName();
    }

    @Override
    public void placeCardOnBoard(final Card card, final int x, final int y) {
        player.placeCardOnBoard(card, x, y);
    }

    @Override
    public void moveCardOnBoard(final Card card, final int fromX, final int fromY, final int toX, final int toY) {
        player.moveCardOnBoard(card, fromX, fromY, toX, toY);
    }

    @Override
    public void takeCardFromBoard(final Card card, final int x, final int y, final CardSlot hint) {
        player.takeCardFromBoard(card, x, y, cardSlots.indexOf(hint));
    }

    @Override
    public void endTurn() {
        player.endTurn();
    }

}
