package com.apixandru.games.rummikub.model;

import com.apixandru.games.rummikub.api.BoardCallback;
import com.apixandru.games.rummikub.api.CompoundCallback;
import com.apixandru.games.rummikub.api.Player;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 25, 2015
 */
public interface Rummikub<H> {

    Player<H> addPlayer(String name, CompoundCallback<H> callback);

    void addBoardCallback(BoardCallback boardCallback);

    void removePlayer(Player<H> player);

}
