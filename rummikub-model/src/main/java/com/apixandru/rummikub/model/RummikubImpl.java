package com.apixandru.rummikub.model;

import com.apixandru.rummikub.api.GameEventListener;
import com.apixandru.rummikub.api.StateChangeListener;
import com.apixandru.rummikub.api.game.GameOverReason;
import com.apixandru.rummikub.api.room.RummikubRoomConfigurer;
import com.apixandru.rummikub.api.room.RummikubRoomListener;
import com.apixandru.rummikub.model.game.GameConfigurerImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.apixandru.rummikub.model.RummikubException.Reason.NAME_TAKEN;
import static com.apixandru.rummikub.model.RummikubException.Reason.NO_LISTENER;
import static com.apixandru.rummikub.model.RummikubException.Reason.NO_NAME;
import static com.apixandru.rummikub.model.RummikubException.Reason.ONGOING_GAME;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 10, 2016
 */
public class RummikubImpl implements GameEventListener, RummikubRoomConfigurer {

    private final Map<String, StateChangeListener> players = new HashMap<>();

    private final List<RummikubRoomListener> waitingRoomListeners = new ArrayList<>();

    private GameConfigurerImpl gameConfigurer;

    private State state = State.WAITING;

    private static boolean isEmpty(final String string) {
        return null == string || string.isEmpty();
    }

    @Deprecated
    public void register(final String playerName, final StateChangeListener listener) throws RummikubException {
        validateCanJoin(playerName, listener);
        addPlayer(playerName, listener);
    }

    public void validateCanJoin(final String playerName, final StateChangeListener listener) {
        if (null == listener) {
            throw new RummikubException(NO_LISTENER);
        }
        if (isEmpty(playerName)) {
            throw new RummikubException(NO_NAME);
        }
        if (players.containsKey(playerName)) {
            throw new RummikubException(NAME_TAKEN);
        }
        if (State.WAITING != state) {
            throw new RummikubException(ONGOING_GAME);
        }
    }

    public void addPlayer(final String playerName, final StateChangeListener listener) {
        players.put(playerName, listener);
        listener.enteredWaitingRoom(this);
        broadcastPlayerJoined(playerName);
    }

    private void broadcastPlayerJoined(final String playerName) {
        waitingRoomListeners
                .forEach(waitingRoomListener -> waitingRoomListener.playerJoined(playerName));
    }

    private void broadcastPlayerLeft(final String playerName) {
        waitingRoomListeners
                .forEach(waitingRoomListener -> waitingRoomListener.playerLeft(playerName));
    }

    private void goToWaitingRoom() {
        players.values()
                .forEach(listener -> listener.enteredWaitingRoom(this));
        state = State.WAITING;
    }

    @Override
    public void newTurn(final String player) {

    }

    @Override
    public void gameOver(final String player, final GameOverReason reason) {
        goToWaitingRoom();
    }

    @Override
    public void startGame() {
        gameConfigurer = new GameConfigurerImpl();
        gameConfigurer.addGameEventListener(this);
        players.values()
                .forEach(listener -> listener.enteredGame(gameConfigurer));
        state = State.PLAYING;
    }

    @Override
    public void registerListener(final RummikubRoomListener listener) {
        waitingRoomListeners.add(listener);
    }

    @Override
    public void unregisterListener(RummikubRoomListener listener) {
        waitingRoomListeners.remove(listener);
    }

    public void unregister(final String playerName) {
        players.remove(playerName); // TODO synchronize deez!
        if (state == State.WAITING) {
            broadcastPlayerLeft(playerName);
        } else {
            gameConfigurer.removePlayer(playerName);
        }
    }

    private enum State {
        WAITING, PLAYING
    }

}
