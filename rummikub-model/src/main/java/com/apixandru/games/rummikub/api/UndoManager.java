package com.apixandru.games.rummikub.api;

import com.apixandru.games.rummikub.api.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 25, 2015
 */
final class UndoManager {

    private final List<UndoAction> undoActions = new ArrayList<>();

    private Card[][] cardsOnBoard;

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
    void undo(PlayerImpl player, Board board) {
        for (UndoAction undoAction : Util.revertedCopy(this.undoActions)) {
            undoAction.undo(player, board);
        }
        reset(board);
    }

    /**
     * @param card
     * @return
     */
    boolean wasOnBoard(final Card card) {
        return streamNotNull(this.cardsOnBoard)
                .collect(Collectors.toList())
                .contains(card);
    }

    /**
     * @param board
     * @return
     */
    public boolean hasChanged(final Board board) {
        return !Arrays.deepEquals(board.cards, cardsOnBoard);
    }

    /**
     * @param board
     * @return
     */
    public boolean justMovedCards(final Board board) {
        return count(board.cards) == count(this.cardsOnBoard);
    }

    /**
     * @param cards
     * @return
     */
    private static long count(final Card[][] cards) {
        return streamNotNull(cards).count();
    }


    /**
     * @return
     */
    private static Stream<Card> streamNotNull(final Card[][] cards) {
        return Arrays.stream(cards)
                .flatMap(Arrays::stream)
                .filter(Objects::nonNull);
    }

    /**
     * @param board
     */
    void reset(final Board board) {
        this.cardsOnBoard = Util.copyOf(board.cards);
        this.undoActions.clear();
    }

    interface UndoAction {
        void undo(PlayerImpl player, Board board);
    }

    static class UndoPlayerToBoard implements UndoAction {

        private final int x, y;

        UndoPlayerToBoard(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public void undo(final PlayerImpl player, final Board board) {
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
        public void undo(final PlayerImpl player, final Board board) {
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
        public void undo(final PlayerImpl player, final Board board) {
            board.moveCard(toX, toY, fromX, fromY);
        }

    }

}
