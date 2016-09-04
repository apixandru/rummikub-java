package com.apixandru.rummikub.model.game;

import com.apixandru.rummikub.api.GameConfigurer;
import com.apixandru.rummikub.api.game.GameEventListener;
import com.apixandru.rummikub.api.game.BoardListener;
import com.apixandru.rummikub.api.game.Player;
import com.apixandru.rummikub.api.game.PlayerCallback;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 11, 2016
 */
public class GameConfigurerImpl implements GameConfigurer {

    private final Rummikub<Integer> rummikubGame = RummikubFactory.newInstance();

    @Override
    public void addGameEventListener(final GameEventListener gameEventListener) {
        rummikubGame.addGameEventListener(gameEventListener);
    }

    @Override
    public void removeGameEventListener(GameEventListener gameEventListener) {
        rummikubGame.removeGameEventListener(gameEventListener);
    }

    @Override
    public void addBoardListener(final BoardListener boardListener) {
        rummikubGame.addBoardListener(boardListener);
    }

    @Override
    public void removeBoardListener(BoardListener boardListener) {
        rummikubGame.removeBoardListener(boardListener);
    }

    @Override
    public Player<Integer> newPlayer(final PlayerCallback<Integer> playerCallback) {
        return rummikubGame.addPlayer(playerCallback.getPlayerName(), playerCallback);
    }

    @Override
    public void removePlayer(Player<Integer> player) {
        rummikubGame.removePlayer(player.getName());
    }

    public void removePlayer(String playerName) {
        rummikubGame.removePlayer(playerName);
    }

}
