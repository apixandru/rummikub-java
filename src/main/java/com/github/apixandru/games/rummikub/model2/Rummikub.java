package com.github.apixandru.games.rummikub.model2;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 25, 2015
 */
public interface Rummikub {

    /**
     * @param name
     * @return
     */
    Player addPlayer(String name);

    /**
     * @param name
     * @param callback
     * @return
     */
    Player addPlayer(String name, PlayerCallback callback);

}
