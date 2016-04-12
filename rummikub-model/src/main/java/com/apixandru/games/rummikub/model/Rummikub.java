package com.apixandru.games.rummikub.model;

import com.apixandru.games.rummikub.api.BoardCallback;
import com.apixandru.games.rummikub.api.GameEventListener;
import com.apixandru.games.rummikub.api.Player;
import com.apixandru.games.rummikub.api.PlayerCallback;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 25, 2015
 */
public interface Rummikub<H> {

    Player<H> addPlayer(String name, PlayerCallback<H> callback);

    void removePlayer(Player<H> player);

    void addBoardCallback(BoardCallback boardCallback);

    void removeBoardCallback(BoardCallback boardCallback);

    void addGameEventListener(GameEventListener gameEventListener);

    void removeGameEventListener(GameEventListener gameEventListener);

}
