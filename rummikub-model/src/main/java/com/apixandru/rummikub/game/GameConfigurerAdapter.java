package com.apixandru.rummikub.game;

import com.apixandru.games.rummikub.api.BoardCallback;
import com.apixandru.games.rummikub.api.GameEventListener;
import com.apixandru.games.rummikub.api.Player;
import com.apixandru.games.rummikub.api.PlayerCallback;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 10, 2016
 */
public class GameConfigurerAdapter<H> implements GameConfigurer<H> {

    @Override
    public void addGameEventListener(final GameEventListener gameEventListener) {

    }

    @Override
    public void addPlayerCallback(final PlayerCallback<H> playerCallback) {

    }

    @Override
    public void addBoardCallback(final BoardCallback boardCallback) {

    }

    @Override
    public Player<H> newPlayer() {
        return null;
    }

}
