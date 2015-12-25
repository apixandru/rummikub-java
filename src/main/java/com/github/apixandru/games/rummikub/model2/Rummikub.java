package com.github.apixandru.games.rummikub.model2;

import com.github.apixandru.games.rummikub.model.Card;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 25, 2015
 */
public interface Rummikub {

    /**
     * @return
     */
    Player currentPlayer();

    /**
     * @param name
     * @return
     */
    Player addPlayer(String name);

    /**
     * @param player
     * @param card
     * @param x
     * @param y
     * @return
     */
    boolean placeCardOnBoard(Player player, Card card, int x, int y);

    /**
     * @param player
     * @return
     */
    Card requestCard(Player player);

}
