package com.apixandru.games.rummikub.api;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 09, 2016
 */
public interface GameEventListener {

    /**
     * @param myTurn indicates if it's my turn
     */
    void newTurn(boolean myTurn);

    /**
     * @param player the player that triggered the event
     * @param quit   if the event was triggered by the player quitting
     * @param me     if I triggered the event
     */
    void gameOver(String player, boolean quit, boolean me);

}
