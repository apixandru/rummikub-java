package com.apixandru.rummikub.swing.local;

import com.apixandru.rummikub.api.BoardListener;
import com.apixandru.rummikub.api.GameEventListener;
import com.apixandru.rummikub.api.Player;
import com.apixandru.rummikub.api.PlayerCallback;
import com.apixandru.rummikub.client.game.GameConfigurer;
import com.apixandru.rummikub.model.Rummikub;
import com.apixandru.rummikub.model.RummikubFactory;

/**
 * @author Alexandru-Constantin Bledea
 * @since Jul 29, 2017
 */
public final class LocalGameConfigurer implements GameConfigurer {

    private final Rummikub<Integer> rummikub = RummikubFactory.newInstance();

    private final String name;

    public LocalGameConfigurer(String name) {
        this.name = name;
    }

    @Override
    public void addGameEventListener(GameEventListener gameEventListener) {
        rummikub.addGameEventListener(gameEventListener);
    }

    @Override
    public void addBoardListener(BoardListener boardListener) {
        rummikub.addBoardListener(boardListener);
    }

    @Override
    public Player<Integer> newPlayer(PlayerCallback<Integer> playerCallback) {
        return rummikub.addPlayer(name, playerCallback);
    }

}
