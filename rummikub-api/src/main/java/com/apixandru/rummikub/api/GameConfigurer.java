package com.apixandru.rummikub.api;

import com.apixandru.rummikub.api.game.BoardListener;
import com.apixandru.rummikub.api.game.Player;
import com.apixandru.rummikub.api.game.PlayerCallback;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 10, 2016
 */
public interface GameConfigurer {

    void addGameEventListener(GameEventListener gameEventListener);

    void removeGameEventListener(GameEventListener gameEventListener);

    void addBoardListener(BoardListener boardListener);

    void removeBoardListener(BoardListener boardListener);

    Player<Integer> newPlayer(PlayerCallback<Integer> playerCallback);

    void removePlayer(Player<Integer> player);

}
