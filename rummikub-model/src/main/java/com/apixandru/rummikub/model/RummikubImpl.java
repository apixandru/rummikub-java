package com.apixandru.rummikub.model;

import com.apixandru.rummikub.api.GameEventListener;
import com.apixandru.rummikub.api.GameOverReason;
import com.apixandru.rummikub.api.StateChangeListener;
import com.apixandru.rummikub.api.WaitingRoomConfigurer;
import com.apixandru.rummikub.api.WaitingRoomListener;
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
public class RummikubImpl implements Rummikub, GameEventListener, WaitingRoomConfigurer {

    private final Map<String, StateChangeListener> players = new HashMap<>();

    private final List<WaitingRoomListener> waitingRoomListeners = new ArrayList<>();

    private GameConfigurerImpl gameConfigurer;

    private State state = State.WAITING;

    private static boolean isEmpty(final String string) {
        return null == string || string.isEmpty();
    }

    @Override
    public void register(final String playerName, final StateChangeListener listener) throws RummikubException {
        validateCanJoin(playerName, listener);
        addPlayer(playerName, listener);
    }

    private void validateCanJoin(final String playerName, final StateChangeListener listener) {
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

    private void addPlayer(final String playerName, final StateChangeListener listener) {
        players.put(playerName, listener);
        broadcastPlayerJoined(playerName);
        listener.enteredWaitingRoom(this);
    }

    private void broadcastPlayerJoined(final String playerName) {
        waitingRoomListeners
                .forEach(waitingRoomListener -> waitingRoomListener.playerJoined(playerName));
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
    public void registerListener(final WaitingRoomListener listener) {
        waitingRoomListeners.add(listener);
    }

    @Override
    public void unregisterListener(WaitingRoomListener listener) {
        waitingRoomListeners.remove(listener);
    }

    public void unregister(final String playerName) {
        gameConfigurer.removePlayer(playerName);
    }

    private enum State {
        WAITING, PLAYING
    }

}
