package com.apixandru.rummikub.server;

import com.apixandru.rummikub.api.PlayerCallback;
import com.apixandru.rummikub.brotocol.room.StartGameListener;
import com.apixandru.rummikub.model.Rummikub;
import com.apixandru.rummikub.server.waiting.Room;
import org.mockito.Mockito;

import static org.mockito.Mockito.mock;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 14, 2016
 */
final class EagerToStartGameListener implements StateChangeListener {

    private StartGameListener startGameListener;

    @Override
    public void enteredWaitingRoom(Room room, StartGameListener startGameListener) {
        this.startGameListener = startGameListener;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void enteredGame(final Rummikub<Integer> rummikub) {
        rummikub.addPlayer("", mock(PlayerCallback.class));
    }

    public void startGame() {
        startGameListener.startGame();
    }
}
