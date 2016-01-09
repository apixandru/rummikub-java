package com.apixandru.games.rummikub.api;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 09, 2016
 */
public interface GameEventListener {

    /**
     * @param myTurn
     */
    void newTurn(boolean myTurn);

    /**
     * @param player
     * @param quit
     * @param me
     */
    void gameOver(String player, boolean quit, boolean me);

}
