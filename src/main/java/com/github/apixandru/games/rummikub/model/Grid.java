package com.github.apixandru.games.rummikub.model;

import static com.github.apixandru.games.rummikub.model.Constants.NUM_COLS;
import static com.github.apixandru.games.rummikub.model.Constants.NUM_ROWS;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 22, 2015
 */
public abstract class Grid {

    final Card[][] cards;

    Grid(final int rows, final int cols) {
        this.cards = new Card[rows][cols];
    }

    /**
     * @param card
     * @param x
     * @param y
     * @return
     */
    public boolean placeCard(Card card, int x, int y) {
        if (!inBounds(x, y)) {
            return false;
        }
        // TODO maybe something more IntelliJ-ent
        if (isFree(x, y) || card == cards[y][x]) {
            cards[y][x] = card;
            return true;
        }
        return false;
    }

    /**
     * @param x
     * @param y
     * @return
     */
    private boolean inBounds(final int x, final int y) {
        return y < NUM_ROWS && x < NUM_COLS;
    }

    /**
     * @param x
     * @param y
     * @return
     */
    public boolean isFree(int x, int y) {
        return null == cards[y][x];
    }

}
