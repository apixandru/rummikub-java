package com.github.apixandru.games.rummikub.ui;

import com.github.apixandru.games.rummikub.model.Board;
import com.github.apixandru.games.rummikub.model.Card;
import com.github.apixandru.games.rummikub.model.listeners.BoardListener;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 18, 2015
 */
public class BoardUi extends JGridPanel {

    /**
     *
     */
    private BoardUi() {
        super(7, 0);
    }

    /**
     * @param board
     * @return
     */
    static BoardUi createBoardUi(final Board board) {
        final BoardUi boardUi = new BoardUi();
        board.addListener(boardUi.new BoardUiListener());
        return boardUi;
    }

    /**
     *
     */
    private class BoardUiListener implements BoardListener {

        @Override
        public void onCardPlacedOnTable(final Card card, final int x, final int y) {
            final CardSlot destination = slots[y][x];
            destination.add(new CardUi(card));
            destination.validate();
        }
    }

}
