package com.apixandru.games.rummikub.ui;

import com.apixandru.games.rummikub.api.Card;
import com.apixandru.games.rummikub.api.PlayerCallback;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 04, 2016
 */
final class CardSlotCallback implements PlayerCallback<CardSlot>, MoveHelper {

    private final List<Card> cardsLockedOnBoard = new ArrayList<>();
    private final List<Card> cardsJustPlacedOnBoard = new ArrayList<>();

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
        this.cardsJustPlacedOnBoard.add(card);
    }

    @Override
    public void onCardRemovedFromBoard(final Card card, final int x, final int y) {
        UiUtil.removeCard(board.slots[y][x]);
        this.cardsJustPlacedOnBoard.remove(card);
    }

    @Override
    public void newTurn(final boolean myTurn) {
        this.btnEndTurn.setEnabled(myTurn);
        this.myTurn.set(myTurn);
        this.cardsLockedOnBoard.addAll(this.cardsJustPlacedOnBoard);
        this.cardsJustPlacedOnBoard.clear();
    }

    @Override
    public boolean canInteractWithBoard() {
        return this.myTurn.get();
    }

    @Override
    public boolean canTakeCardFromBoard(final Card card) {
        return canInteractWithBoard() && !this.cardsLockedOnBoard.contains(card);
    }

}
