package com.apixandru.games.rummikub.model;

import com.apixandru.games.rummikub.api.Card;
import com.apixandru.games.rummikub.api.Player;
import com.apixandru.games.rummikub.api.PlayerCallback;

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

    /**
     * @param player
     * @param <H>
     */
    <H> void removePlayer(Player<H> player);

    // temporary
    List<Card> getCards();

}
