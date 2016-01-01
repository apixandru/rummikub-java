package com.github.apixandru.games.rummikub.model;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 25, 2015
 */
public interface Rummikub {

    /**
     * @param name
     * @return
     */
    Player<Object> addPlayer(String name);

    /**
     * @param name
     * @param callback
     * @return
     */
    <H> Player<H> addPlayer(String name, PlayerCallback<H> callback);

}