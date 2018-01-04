package com.apixandru.rummikub.server;

import com.apixandru.rummikub.api.GameEventListener;
import com.apixandru.rummikub.api.GameOverReason;
import com.apixandru.rummikub.api.room.StartGameListener;
import com.apixandru.rummikub.model.Rummikub;
import com.apixandru.rummikub.model.RummikubFactory;
import com.apixandru.rummikub.server.waiting.Room;

import java.util.HashMap;
import java.util.Map;

import static com.apixandru.rummikub.server.RummikubException.Reason.NAME_TAKEN;
import static com.apixandru.rummikub.server.RummikubException.Reason.NO_NAME;
import static com.apixandru.rummikub.server.RummikubException.Reason.ONGOING_GAME;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 10, 2016
 */
public class RummikubImpl implements StartGameListener {

    private final Map<String, StateChangeListener> players = new HashMap<>();

    private Rummikub<Integer> rummikubGame;
    private Room room;

    public RummikubImpl() {
        goToWaitingRoom();
    }

    private static boolean isEmpty(final String string) {
        return null == string || string.isEmpty();
    }

    public void validateCanJoin(final String playerName) {
        if (isEmpty(playerName)) {
            throw new RummikubException(NO_NAME);
        }
        if (players.containsKey(playerName)) {
            throw new RummikubException(NAME_TAKEN);
        }
        if (null != rummikubGame) {
            throw new RummikubException(ONGOING_GAME);
        }
    }

    public void addPlayer(final String playerName, final StateChangeListener listener) {
        enterWaitingRoom(listener);
        players.put(playerName, listener);
    }

    private void enterWaitingRoom(StateChangeListener listener) {
        listener.enteredWaitingRoom(room, this);
    }

    @Override
    public void startGame() {
        rummikubGame = RummikubFactory.newInstance();

        players.values()
                .forEach(listener -> listener.enteredGame(rummikubGame));

        rummikubGame.addGameEventListener(new RummikubServerGameEventListener());
        room = null;

    }

    public void unregister(final String playerName) {
        players.remove(playerName); // TODO synchronize deez!
    }

    private void goToWaitingRoom() {
        room = new Room();
        rummikubGame = null;
    }

    private class RummikubServerGameEventListener implements GameEventListener {

        @Override
        public void newTurn(String player) {
        }

        @Override
        public void gameOver(String player, GameOverReason reason) {
            goToWaitingRoom();
            players.values()
                    .forEach(RummikubImpl.this::enterWaitingRoom);
        }

    }

}
