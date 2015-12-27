package com.github.apixandru.games.rummikub.ui;

import com.github.apixandru.games.rummikub.model.Card;
import com.github.apixandru.games.rummikub.model2.BoardCallback;

import static com.github.apixandru.games.rummikub.model.Constants.NUM_ROWS;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 18, 2015
 */
public class BoardUi extends JGridPanel implements BoardCallback {

    /**
     *
     */
    BoardUi() {
        super(null, NUM_ROWS, 0);
    }

    @Override
    public void cardPlacedOnBoard(final Card card, final int x, final int y) {
        UiUtil.placeCard(new CardUi(card), slots[y][x]);
    }

}
