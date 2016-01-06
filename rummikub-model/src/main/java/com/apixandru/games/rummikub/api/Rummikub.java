package com.apixandru.games.rummikub.api;

import java.util.List;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 25, 2015
 */
public interface Rummikub {

    /**
     * @param name
     * @param callback
     * @return
     */
    <H> Player<H> addPlayer(String name, PlayerCallback<H> callback);

    // temporary
    List<Card> getCards();

}
