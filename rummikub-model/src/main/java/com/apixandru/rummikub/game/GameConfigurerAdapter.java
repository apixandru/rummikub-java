package com.apixandru.rummikub.game;

import com.apixandru.games.rummikub.api.BoardListener;
import com.apixandru.games.rummikub.api.GameEventListener;
import com.apixandru.games.rummikub.api.Player;
import com.apixandru.games.rummikub.api.PlayerCallback;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 10, 2016
 */
public class GameConfigurerAdapter implements GameConfigurer {

    @Override
    public void addGameEventListener(final GameEventListener gameEventListener) {

    }

    @Override
    public void addBoardListener(final BoardListener boardListener) {

    }

    @Override
    public Player<Integer> newPlayer(final PlayerCallback<Integer> playerCallback) {
        return null;
    }

}
