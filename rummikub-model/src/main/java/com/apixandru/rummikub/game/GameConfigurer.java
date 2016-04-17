package com.apixandru.rummikub.game;

import com.apixandru.games.rummikub.api.BoardListener;
import com.apixandru.games.rummikub.api.GameEventListener;
import com.apixandru.games.rummikub.api.Player;
import com.apixandru.games.rummikub.api.PlayerCallback;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 10, 2016
 */
public interface GameConfigurer<H> {

    void addGameEventListener(GameEventListener gameEventListener);

    void addBoardCallback(BoardListener boardListener);

    Player<H> newPlayer(PlayerCallback<H> playerCallback);

}
