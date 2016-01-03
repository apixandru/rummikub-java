package com.github.apixandru.games.rummikub.model;

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
     */
    void onCardRemovedFromBoard(Card card, int x, int y);

}
