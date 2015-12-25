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

}
