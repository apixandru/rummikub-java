package com.github.apixandru.games.rummikub.model2;

import com.github.apixandru.games.rummikub.model.Card;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 25, 2015
 */
public interface PlayerListener {

    /**
     * @param player
     */
    void requestEndTurn(Player player);

    /**
     * @param player
     * @param card
     * @param x
     * @param y
     * @return
     */
    void placeCardOnBoard(Player player, Card card, int x, int y);

    /**
     * @param player
     * @param card
     * @param fromX
     * @param fromY
     * @param toX
     * @param toY
     */
    void moveCardOnBoard(Player player, Card card, int fromX, int fromY, int toX, int toY);

}
