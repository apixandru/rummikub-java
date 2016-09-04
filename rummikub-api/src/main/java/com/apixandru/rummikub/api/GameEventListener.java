package com.apixandru.rummikub.api;

import com.apixandru.rummikub.api.game.GameOverReason;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 09, 2016
 */
public interface GameEventListener {

    /**
     * @param player the player
     */
    void newTurn(String player);

    /**
     * @param player the player that triggered the event
     * @param reason why is the game over
     */
    void gameOver(String player, GameOverReason reason);

}
