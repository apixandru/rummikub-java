package com.apixandru.rummikub2;

import com.apixandru.games.rummikub.api.GameEventListener;
import com.apixandru.games.rummikub.api.GameOverReason;
import com.apixandru.rummikub.StateChangeListener;
import com.apixandru.rummikub.waiting.StartGameListener;
import com.apixandru.rummikub.waiting.WaitingRoomConfigurator;
import com.apixandru.rummikub.waiting.WaitingRoomListener;
import com.apixandru.rummikub2.game.GameConfigurerImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 10, 2016
 */
class RummikubImpl implements Rummikub<Integer>, GameEventListener, StartGameListener, WaitingRoomConfigurator {

    private final Map<String, StateChangeListener<Integer>> players = new HashMap<>();

    private final List<WaitingRoomListener> waitingRoomListeners = new ArrayList<>();

    private State state = State.WAITING;

    private static boolean isEmpty(final String string) {
        return null == string || string.isEmpty();
    }

    @Override
    public void register(final String playerName, final StateChangeListener<Integer> listener) throws RummikubException {
        validateCanJoin(playerName, listener);
        addPlayer(playerName, listener);
    }

    private void validateCanJoin(final String playerName, final StateChangeListener<Integer> listener) {
        if (null == listener) {
            throw new RummikubException("Listener is missing.");
        }
        if (isEmpty(playerName)) {
            throw new RummikubException("Name is missing.");
        }
        if (players.containsKey(playerName)) {
            throw new RummikubException("Name already taken.");
        }
        if (State.WAITING != state) {
            throw new RummikubException("There is an ongoing game, try later.");
        }
    }

    private void addPlayer(final String playerName, final StateChangeListener<Integer> listener) {
        players.put(playerName, listener);
        broadcastPlayerJoined(playerName);
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
        final GameConfigurerImpl gameConfigurer = new GameConfigurerImpl();
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
    public StartGameListener newStartGameListener() {
        return this;
    }

    private enum State {
        WAITING, PLAYING
    }

}
