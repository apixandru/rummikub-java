package com.github.apixandru.games.rummikub.ui;

import com.github.apixandru.games.rummikub.model.BoardCallback;
import com.github.apixandru.games.rummikub.model.Card;

import static com.github.apixandru.games.rummikub.model.Constants.NUM_ROWS;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 18, 2015
 */
public class BoardUi extends JGridPanel implements BoardCallback {

    BoardUi() {
        super(NUM_ROWS, 0);
    }

    @Override
    public void onCardPlacedOnBoard(final Card card, final int x, final int y) {
        UiUtil.placeCard(new CardUi(card), slots[y][x]);
    }

    @Override
    public void onCardRemovedFromBoard(final Card card, final int x, final int y) {
        UiUtil.removeCard(slots[y][x]);
    }

}
