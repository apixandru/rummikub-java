package com.apixandru.games.rummikub.api;

/**
 * @param <H> the hint type
 * @author Alexandru-Constantin Bledea
 * @since December 25, 2015
 */
public interface Player<H> {

    /**
     * @return the player name
     */
    String getName();

    /**
     * @param card the card
     * @param x    the column on the board
     * @param y    the row on the board
     */
    void placeCardOnBoard(Card card, int x, int y);

    /**
     * @param card  the card
     * @param fromX the current column on the board
     * @param fromY the current row on the board
     * @param toX   the destination column on the board
     * @param toY   the destination row on the board
     */
    void moveCardOnBoard(Card card, int fromX, int fromY, int toX, int toY);

    /**
     * @param card the card
     * @param x    the column on the board
     * @param y    the row on the board
     * @param hint where the card should be placed in the hand
     */
    void takeCardFromBoard(Card card, int x, int y, H hint);

    /**
     *
     */
    void endTurn();

}
