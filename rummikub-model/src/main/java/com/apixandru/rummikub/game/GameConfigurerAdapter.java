package com.apixandru.rummikub.game;

import com.apixandru.games.rummikub.api.BoardListener;
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
    public void addBoardCallback(final BoardListener boardListener) {

    }

    @Override
    public Player<H> newPlayer(final PlayerCallback<H> playerCallback) {
        return null;
    }

}
