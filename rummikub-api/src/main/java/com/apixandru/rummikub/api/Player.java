package com.apixandru.rummikub.api;

/**
 * @param <H> the hint type
 * @author Alexandru-Constantin Bledea
 * @since December 25, 2015
 */
public interface Player<H> {

    String getName();

    void placeCardOnBoard(Card card, int x, int y);

    void moveCardOnBoard(Card card, int fromX, int fromY, int toX, int toY);

    void takeCardFromBoard(Card card, int x, int y, H hint);

    void endTurn();

}
