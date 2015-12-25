package com.github.apixandru.games.rummikub.model2;

import com.github.apixandru.games.rummikub.model.Card;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 25, 2015
 */
public class UndoManager {

    interface UndoAction {
        void undo(PlayerImpl player, BoardImpl board);
    }

    private class UndoPlayerToBoard implements UndoAction {

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

    private class UndoBoardToPlayer implements UndoAction {
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

    private class UndoBoardToBoard implements UndoAction {

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
