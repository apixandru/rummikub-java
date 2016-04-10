package com.apixandru.rummikub.game;

import com.apixandru.games.rummikub.api.BoardCallback;
import com.apixandru.games.rummikub.api.GameEventListener;
import com.apixandru.games.rummikub.api.Player;
import com.apixandru.games.rummikub.api.PlayerCallback;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 10, 2016
 */
public interface GameConfigurer<H> {

    void addGameEventListener(GameEventListener gameEventListener);

    void addPlayerCallback(PlayerCallback<H> playerCallback);

    void addBoardCallback(BoardCallback boardCallback);

    Player<H> newPlayer();

}
