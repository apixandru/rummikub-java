package com.apixandru.games.rummikub.api;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 09, 2016
 */
public interface GameEventListener {

    /**
     * @param player the player
     * @param myTurn indicates if it's my turn
     */
    void newTurn(String player, boolean myTurn);

    /**
     * @param player the player that triggered the event
     * @param reason why is the game oveer
     * @param me     if I triggered the event
     */
    void gameOver(String player, GameOverReason reason, boolean me);

}
