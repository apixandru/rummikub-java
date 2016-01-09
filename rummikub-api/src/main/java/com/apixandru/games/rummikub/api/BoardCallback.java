package com.apixandru.games.rummikub.api;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 27, 2015
 */
public interface BoardCallback {

    /**
     * @param card
     * @param x
     * @param y
     */
    void onCardPlacedOnBoard(Card card, int x, int y);

    /**
     * @param card
     * @param x
     * @param y
     * @param reset
     */
    void onCardRemovedFromBoard(Card card, int x, int y, boolean reset);

}
