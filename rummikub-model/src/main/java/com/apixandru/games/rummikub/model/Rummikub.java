package com.apixandru.games.rummikub.model;

import com.apixandru.games.rummikub.api.Card;
import com.apixandru.games.rummikub.api.Player;
import com.apixandru.games.rummikub.api.PlayerCallback;

import java.util.List;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 25, 2015
 */
public interface Rummikub<H> {

    /**
     * @param name
     * @param callback
     * @return
     */
    Player<H> addPlayer(String name, PlayerCallback<H> callback);

    /**
     * @param player
     */
    void removePlayer(Player<H> player);

    // temporary
    List<Card> getCards();

}
