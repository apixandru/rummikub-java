package com.apixandru.rummikub.model;

import com.apixandru.rummikub.api.Card;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 25, 2015
 */
final class UndoManager {

    private final List<UndoAction> undoActions = new ArrayList<>();

    private Card[][] cardsOnBoard;

    private static long count(final Card[][] cards) {
        return streamNotNull(cards)
                .count();
    }

    private static Stream<Card> streamNotNull(final Card[][] cards) {
        return Arrays.stream(cards)
                .flatMap(Arrays::stream)
                .filter(Objects::nonNull);
    }

    void addAction(UndoAction action) {
        this.undoActions.add(action);
    }

    void undo(PlayerImpl player, Board board) {
        for (UndoAction undoAction : Util.revertedCopy(this.undoActions)) {
            undoAction.undo(player, board);
        }
        reset(board);
    }

    boolean wasOnBoard(final Card card) {
        return streamNotNull(this.cardsOnBoard)
                .anyMatch(card::equals);
    }

    boolean hasChanged(final Board board) {
        return !Arrays.deepEquals(board.cards, cardsOnBoard);
    }

    boolean justMovedCards(final Board board) {
        return count(board.cards) == count(this.cardsOnBoard);
    }

    void reset(final Board board) {
        this.cardsOnBoard = Util.copyOf(board.cards);
        this.undoActions.clear();
    }

    interface UndoAction {
        void undo(EnhancedPlayer player, Board board);
    }

    static class UndoPlayerToBoard implements UndoAction {

        private final int x, y;

        UndoPlayerToBoard(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public void undo(final EnhancedPlayer player, final Board board) {
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
        public void undo(final EnhancedPlayer player, final Board board) {
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
        public void undo(final EnhancedPlayer player, final Board board) {
            board.moveCard(toX, toY, fromX, fromY);
        }

    }

}
