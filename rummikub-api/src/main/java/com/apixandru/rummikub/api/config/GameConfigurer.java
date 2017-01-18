package com.apixandru.rummikub.api.config;

import com.apixandru.rummikub.api.game.BoardListener;
import com.apixandru.rummikub.api.game.GameEventListener;
import com.apixandru.rummikub.api.game.Player;
import com.apixandru.rummikub.api.game.PlayerCallback;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 10, 2016
 */
public interface GameConfigurer {

    void addGameEventListener(GameEventListener gameEventListener);

    void addBoardListener(BoardListener boardListener);

    Player<Integer> newPlayer(PlayerCallback<Integer> playerCallback);

}
