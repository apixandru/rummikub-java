package com.apixandru.rummikub.model.game;

import com.apixandru.rummikub.api.game.GameEventListener;
import com.apixandru.rummikub.api.game.BoardListener;
import com.apixandru.rummikub.api.game.Player;
import com.apixandru.rummikub.api.game.PlayerCallback;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 25, 2015
 */
public interface Rummikub<H> {

    Player<H> addPlayer(String name, PlayerCallback<H> callback);

    void removePlayer(String playerName);

    void addBoardListener(BoardListener boardListener);

    void removeBoardListener(BoardListener boardListener);

    void addGameEventListener(GameEventListener gameEventListener);

    void removeGameEventListener(GameEventListener gameEventListener);

}
