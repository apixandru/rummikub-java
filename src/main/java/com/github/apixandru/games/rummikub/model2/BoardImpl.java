package com.github.apixandru.games.rummikub.model2;

import com.github.apixandru.games.rummikub.model.Card;

import static com.github.apixandru.games.rummikub.model.Constants.NUM_COLS;
import static com.github.apixandru.games.rummikub.model.Constants.NUM_ROWS;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 25, 2015
 */
class BoardImpl extends Grid implements Board {

    /**
     *
     */
    BoardImpl() {
        super(NUM_ROWS, NUM_COLS);
    }

    @Override
    public void lockPieces() {

    }

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public boolean placeCard(final Card card, final int x, final int y) {
        return false;
    }

}
