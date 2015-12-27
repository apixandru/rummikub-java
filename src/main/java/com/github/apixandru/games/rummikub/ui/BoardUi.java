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

    /**
     * @param board
     */
    private BoardUi(final Board board) {
        super(board, NUM_ROWS, 0);
    }

    /**
     * @param board
     * @return
     */
    static BoardUi createBoardUi(final Board board) {
        return new BoardUi(board);
    }

    /**
     * @param card
     * @param x
     * @param y
     */
    void placeCard(final Card card, final int x, final int y) {
        UiUtil.placeCard(new CardUi(card), slots[y][x]);
    }

}
