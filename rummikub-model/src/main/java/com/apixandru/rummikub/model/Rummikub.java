package com.apixandru.rummikub.model;

import com.apixandru.rummikub.api.BoardListener;
import com.apixandru.rummikub.api.GameEventListener;
import com.apixandru.rummikub.api.Player;
import com.apixandru.rummikub.api.PlayerCallback;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 25, 2015
 */
public interface Rummikub<H> {

    void setNextPlayer();

    Player<H> addPlayer(String name, PlayerCallback<H> callback);

    void removePlayer(Player<H> player);

    void addBoardListener(BoardListener boardListener);

    void removeBoardListener(BoardListener boardListener);

    void addGameEventListener(GameEventListener gameEventListener);

    void removeGameEventListener(GameEventListener gameEventListener);

}
