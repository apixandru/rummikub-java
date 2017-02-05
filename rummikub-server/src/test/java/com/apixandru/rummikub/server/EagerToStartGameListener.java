package com.apixandru.rummikub.server;

import com.apixandru.rummikub.api.room.StartGameListener;
import com.apixandru.rummikub.model.Rummikub;
import com.apixandru.rummikub.server.room.Room;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 14, 2016
 */
final class EagerToStartGameListener implements StateChangeListener {

    @Override
    public void enteredWaitingRoom(Room room, StartGameListener startGameListener) {
        startGameListener.startGame();
    }

    @Override
    public void enteredGame(final Rummikub<Integer> rummikub) {
    }

}
