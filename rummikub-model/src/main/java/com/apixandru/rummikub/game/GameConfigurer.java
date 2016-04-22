package com.apixandru.rummikub.game;

import com.apixandru.rummikub.api.BoardListener;
import com.apixandru.rummikub.api.GameEventListener;
import com.apixandru.rummikub.api.Player;
import com.apixandru.rummikub.api.PlayerCallback;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 10, 2016
 */
public interface GameConfigurer {

    void addGameEventListener(GameEventListener gameEventListener);

    void addBoardListener(BoardListener boardListener);

    Player<Integer> newPlayer(PlayerCallback<Integer> playerCallback);

}
