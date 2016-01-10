package com.apixandru.games.rummikub.model;

import com.apixandru.games.rummikub.api.Card;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 25, 2015
 */
interface PlayerListener {

    /**
     * @param player
     */
    void requestEndTurn(PlayerImpl player);

    /**
     * @param player
     * @param card
     * @param x
     * @param y
     * @return
     */
    void placeCardOnBoard(PlayerImpl player, Card card, int x, int y);

    /**
     * @param player
     * @param card
     * @param x
     * @param y
     * @param hint
     */
    void takeCardFromBoard(PlayerImpl player, Card card, int x, int y, final Integer hint);

    /**
     * @param player
     * @param card
     * @param fromX
     * @param fromY
     * @param toX
     * @param toY
     */
    void moveCardOnBoard(PlayerImpl player, Card card, int fromX, int fromY, int toX, int toY);

}
