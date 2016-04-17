package com.apixandru.rummikub2.game;

import com.apixandru.games.rummikub.api.BoardListener;
import com.apixandru.games.rummikub.api.GameEventListener;
import com.apixandru.games.rummikub.api.Player;
import com.apixandru.games.rummikub.api.PlayerCallback;
import com.apixandru.games.rummikub.model.Rummikub;
import com.apixandru.games.rummikub.model.RummikubFactory;
import com.apixandru.rummikub.game.GameConfigurer;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 11, 2016
 */
public class GameConfigurerImpl implements GameConfigurer<Integer> {

    private final Rummikub<Integer> rummikubGame = RummikubFactory.newInstance();

    @Override
    public void addGameEventListener(final GameEventListener gameEventListener) {
        rummikubGame.addGameEventListener(gameEventListener);
    }

    @Override
    public void addBoardListener(final BoardListener boardListener) {
        rummikubGame.addBoardListener(boardListener);
    }

    @Override
    public Player<Integer> newPlayer(final PlayerCallback<Integer> playerCallback) {
        return rummikubGame.addPlayer(playerCallback.getPlayerName(), playerCallback);
    }

}
