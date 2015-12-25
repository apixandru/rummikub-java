package com.github.apixandru.games.rummikub.model2;

import com.github.apixandru.games.rummikub.model.Card;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 25, 2015
 */
public interface Player {

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
     *
     */
    void endTurn();

}
