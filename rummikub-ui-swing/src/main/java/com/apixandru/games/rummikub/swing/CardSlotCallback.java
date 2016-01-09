package com.apixandru.games.rummikub.swing;

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

    private final JGridPanel board;
    private final PlayerUi player;
    private final JButton btnEndTurn;

    private AtomicBoolean myTurn = new AtomicBoolean();

    /**
     * @param board
     * @param player
     * @param btnEndTurn
     */
    public CardSlotCallback(final JGridPanel board, final PlayerUi player, final JButton btnEndTurn) {
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
    public void onCardRemovedFromBoard(final Card card, final int x, final int y, final boolean reset) {
        UiUtil.removeCard(board.slots[y][x]);
        this.cardsJustPlacedOnBoard.remove(card);
        if (reset) {
            this.cardsLockedOnBoard.remove(card);
        }
    }

    @Override
    public void newTurn(final boolean myTurn) {
        this.btnEndTurn.setEnabled(myTurn);
        this.myTurn.set(myTurn);
        this.cardsLockedOnBoard.addAll(this.cardsJustPlacedOnBoard);
        this.cardsJustPlacedOnBoard.clear();
    }

    @Override
    public void gameOver(final String player, final boolean quit, final boolean me) {
        String message;
        int icon;
        if (quit) {
            message = player + " quit the game.";
            icon = JOptionPane.ERROR_MESSAGE;
        } else if (me) {
            message = "You won!";
            icon = JOptionPane.INFORMATION_MESSAGE;
        } else {
            message = player + " won.";
            icon = JOptionPane.INFORMATION_MESSAGE;
        }
        JOptionPane.showMessageDialog(null, message, "Game Over", icon);
        System.exit(1);
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
