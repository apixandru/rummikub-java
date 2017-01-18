package com.apixandru.rummikub.server.game;

import com.apixandru.rummikub.api.game.BoardListener;
import com.apixandru.rummikub.api.game.GameEventListener;
import com.apixandru.rummikub.api.game.Player;
import com.apixandru.rummikub.api.game.PlayerCallback;
import com.apixandru.rummikub.model.Rummikub;

/**
 * @author Alexandru-Constantin Bledea
 * @since May 06, 2016
 */
public class TrackingGameConfigurer {

    private final Rummikub<Integer> gameConfigurer;

    private GameEventListener gameEventListener;
    private BoardListener boardListener;
    private Player<Integer> player;

    public TrackingGameConfigurer(Rummikub<Integer> gameConfigurer) {
        this.gameConfigurer = gameConfigurer;
    }

    public void addGameEventListener(GameEventListener gameEventListener) {
        this.gameEventListener = gameEventListener;
        gameConfigurer.addGameEventListener(gameEventListener);
    }

    private void removeGameEventListener(GameEventListener gameEventListener) {
        this.gameEventListener = null;
        gameConfigurer.removeGameEventListener(gameEventListener);
    }

    public void addBoardListener(BoardListener boardListener) {
        this.boardListener = boardListener;
        gameConfigurer.addBoardListener(boardListener);
    }

    private void removeBoardListener(BoardListener boardListener) {
        this.boardListener = null;
        gameConfigurer.removeBoardListener(boardListener);
    }

    public Player<Integer> newPlayer(PlayerCallback<Integer> playerCallback) {
        Player<Integer> integerPlayer = gameConfigurer.addPlayer(playerCallback.getPlayerName(), playerCallback);
        this.player = integerPlayer;
        return integerPlayer;
    }

    private void removePlayer(Player<Integer> player) {
        this.player = null;
        gameConfigurer.removePlayer(player.getName());
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
