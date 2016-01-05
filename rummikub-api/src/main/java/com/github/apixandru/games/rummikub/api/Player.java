package com.github.apixandru.games.rummikub.api;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 25, 2015
 */
public interface Player<H> {

    /**
     * @param card
     * @param x
     * @param y
     * @return
     */
    void placeCardOnBoard(Card card, int x, int y);

    /**
     * @param card
     * @param fromX
     * @param fromY
     * @param toX
     * @param toY
     * @return
     */
    void moveCardOnBoard(Card card, int fromX, int fromY, int toX, int toY);

    /**
     * @param card
     * @param x
     * @param y
     * @param hint
     */
    void takeCardFromBoard(Card card, int x, int y, H hint);

    /**
     *
     */
    void endTurn();

    /**
     * @param card
     * @return
     */
    boolean canMoveCardOffBoard(Card card);

}
