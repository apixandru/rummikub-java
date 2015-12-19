package com.github.apixandru.games.rummikub.ui;

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
    BoardUi() {
        super(7, 0);
    }

    private class BoardUiListener implements BoardListener {

        @Override
        public void onCardPlacedOnTable(final Card card, final int x, final int y) {

//            destination.add(this.draggablePiece);
//            destination.validate();
        }
    }

}
