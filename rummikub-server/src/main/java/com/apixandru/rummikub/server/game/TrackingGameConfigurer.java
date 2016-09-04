package com.apixandru.rummikub.server.game;

import com.apixandru.rummikub.api.GameConfigurer;
import com.apixandru.rummikub.api.game.GameEventListener;
import com.apixandru.rummikub.api.game.BoardListener;
import com.apixandru.rummikub.api.game.Player;
import com.apixandru.rummikub.api.game.PlayerCallback;

/**
 * @author Alexandru-Constantin Bledea
 * @since May 06, 2016
 */
public class TrackingGameConfigurer implements GameConfigurer {

    private final GameConfigurer gameConfigurer;

    private GameEventListener gameEventListener;
    private BoardListener boardListener;
    private Player<Integer> player;

    public TrackingGameConfigurer(GameConfigurer gameConfigurer) {
        this.gameConfigurer = gameConfigurer;
    }

    @Override
    public void addGameEventListener(GameEventListener gameEventListener) {
        this.gameEventListener = gameEventListener;
        gameConfigurer.addGameEventListener(gameEventListener);
    }

    @Override
    public void removeGameEventListener(GameEventListener gameEventListener) {
        this.gameEventListener = null;
        gameConfigurer.removeGameEventListener(gameEventListener);
    }

    @Override
    public void addBoardListener(BoardListener boardListener) {
        this.boardListener = boardListener;
        gameConfigurer.addBoardListener(boardListener);
    }

    @Override
    public void removeBoardListener(BoardListener boardListener) {
        this.boardListener = null;
        gameConfigurer.removeBoardListener(boardListener);
    }

    @Override
    public Player<Integer> newPlayer(PlayerCallback<Integer> playerCallback) {
        Player<Integer> integerPlayer = gameConfigurer.newPlayer(playerCallback);
        this.player = integerPlayer;
        return integerPlayer;
    }

    @Override
    public void removePlayer(Player<Integer> player) {
        this.player = null;
        gameConfigurer.removePlayer(player);
    }

    public void cleanup() {
        if (null != boardListener) {
            removeBoardListener(boardListener);
        }
        if (null != gameEventListener) {
            removeGameEventListener(gameEventListener);
        }
        if (null != player) {
            removePlayer(player);
        }
    }
}
