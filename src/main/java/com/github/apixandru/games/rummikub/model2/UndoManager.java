package com.github.apixandru.games.rummikub.model2;

import com.github.apixandru.games.rummikub.model.Card;
import com.github.apixandru.games.rummikub.model.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 25, 2015
 */
final class UndoManager {

    private final List<UndoAction> undoActions = new ArrayList<>();

    Card[][] cardsOnBoard;

    /**
     * @param action
     */
    void addAction(UndoAction action) {
        this.undoActions.add(action);
    }

    /**
     * @param player
     * @param board
     */
    void undo(PlayerImpl player, BoardImpl board) {
        for (UndoAction undoAction : Util.revertedCopy(undoActions)) {
            undoAction.undo(player, board);
        }
        reset(board);
    }

    /**
     * @param board
     * @return
     */
    public boolean hasChanged(final BoardImpl board) {
        return !Arrays.deepEquals(board.cards, cardsOnBoard);
    }

    /**
     * @return
     */
    public boolean hasUndoActions() {
        return !this.undoActions.isEmpty();
    }

    /**
     * @param board
     */
    void reset(final BoardImpl board) {
        this.cardsOnBoard = Util.copyOf(board.cards);
        this.undoActions.clear();
    }

    interface UndoAction {
        void undo(PlayerImpl player, BoardImpl board);
    }

    static class UndoPlayerToBoard implements UndoAction {

        private final int x, y;

        UndoPlayerToBoard(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public void undo(final PlayerImpl player, final BoardImpl board) {
            Card card = board.removeCard(x, y);
            player.receiveCard(card);
        }

    }

    static class UndoBoardToPlayer implements UndoAction {
        private final int x, y;
        private final Card card;

        UndoBoardToPlayer(final Card card, int x, int y) {
            this.card = card;
            this.x = x;
            this.y = y;
        }

        @Override
        public void undo(final PlayerImpl player, final BoardImpl board) {
            board.placeCard(card, x, y);
        }

    }

    static class UndoBoardToBoard implements UndoAction {

        private final int fromX, fromY, toX, toY;

        UndoBoardToBoard(int fromX, int fromY, int toX, int toY) {
            this.fromX = fromX;
            this.fromY = fromY;
            this.toX = toX;
            this.toY = toY;
        }

        @Override
        public void undo(final PlayerImpl player, final BoardImpl board) {
            board.moveCard(toX, toY, fromX, fromY);
        }

    }

}
