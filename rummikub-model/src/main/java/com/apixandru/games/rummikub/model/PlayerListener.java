package com.apixandru.games.rummikub.model;

import com.apixandru.games.rummikub.api.Card;
import com.apixandru.games.rummikub.api.Player;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 25, 2015
 */
interface PlayerListener {

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
     * @param x
     * @param y
     * @param hint
     */
    void takeCardFromBoard(Player player, Card card, int x, int y, final Object hint);

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
