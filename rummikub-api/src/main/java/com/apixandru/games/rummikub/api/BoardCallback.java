package com.apixandru.games.rummikub.api;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 27, 2015
 */
public interface BoardCallback {

    /**
     * @param card the card that
     * @param x    the x column on the board
     * @param y    the row on the board
     */
    void onCardPlacedOnBoard(Card card, int x, int y);

    /**
     * @param card  the card that
     * @param x     the x column on the board
     * @param y     the row on the board
     * @param reset was the event triggered by the board getting reset
     */
    void onCardRemovedFromBoard(Card card, int x, int y, boolean reset);

}
