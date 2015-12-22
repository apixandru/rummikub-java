package com.github.apixandru.games.rummikub.ui;

import com.github.apixandru.games.rummikub.model.Board;
import com.github.apixandru.games.rummikub.model.Card;
import com.github.apixandru.games.rummikub.model.listeners.CardLocationListener;

import static com.github.apixandru.games.rummikub.model.Constants.NUM_ROWS;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 18, 2015
 */
public class BoardUi extends JGridPanel {

    private final Board board;

    /**
     * @param board
     */
    private BoardUi(final Board board) {
        super(NUM_ROWS, 0);
        this.board = board;
    }

    /**
     * @param board
     * @return
     */
    static BoardUi createBoardUi(final Board board) {
        final BoardUi boardUi = new BoardUi(board);
        board.addListener(boardUi.new BoardUiListener());
        return boardUi;
    }

    void placeCard(final CardUi card, final CardSlot destination) {
        board.placeCard(card.card, destination.x, destination.y);
    }

    /**
     *
     */
    private class BoardUiListener implements CardLocationListener {

        @Override
        public void onCardPlaced(final Card card, final int x, final int y) {
            UiUtil.placeCard(new CardUi(card), slots[y][x]);
        }
    }

}
