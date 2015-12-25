package com.github.apixandru.games.rummikub.model2;

import com.github.apixandru.games.rummikub.model.Card;

import static com.github.apixandru.games.rummikub.model.Constants.NUM_COLS;
import static com.github.apixandru.games.rummikub.model.Constants.NUM_ROWS;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 25, 2015
 */
abstract class Grid {

    final Card[][] cards;

    /**
     * @param rows
     * @param cols
     */
    Grid(final int rows, final int cols) {
        this.cards = new Card[rows][cols];
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
    private boolean isFree(int x, int y) {
        return null == cards[y][x];
    }


    /**
     * @param card
     * @param x
     * @param y
     * @return
     */
    boolean placeCard(Card card, int x, int y) {
        if (inBounds(x, y) && isFree(x, y) || card == cards[y][x]) {
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
    Card removeCard(final int x, final int y) {
        final Card card = cards[y][x];
        cards[y][x] = null;
        return card;
    }

    /**
     * @param fromX
     * @param fromY
     * @param toX
     * @param toY
     */
    public void moveCard(final int fromX, final int fromY, final int toX, final int toY) {
        final Card card = removeCard(fromX, fromY);
        placeCard(card, toX, toY);
    }

}
