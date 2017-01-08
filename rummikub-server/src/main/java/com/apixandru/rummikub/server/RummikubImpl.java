package com.apixandru.rummikub.server;

import com.apixandru.rummikub.api.config.RummikubRoomConfigurer;
import com.apixandru.rummikub.api.room.RummikubRoomListener;
import com.apixandru.rummikub.model.GameConfigurerImpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.apixandru.rummikub.server.RummikubException.Reason.NAME_TAKEN;
import static com.apixandru.rummikub.server.RummikubException.Reason.NO_NAME;
import static com.apixandru.rummikub.server.RummikubException.Reason.ONGOING_GAME;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 10, 2016
 */
public class RummikubImpl implements RummikubRoomConfigurer {

    private final Map<String, StateChangeListener> players = new HashMap<>();

    private final List<RummikubRoomListener> waitingRoomListeners = new ArrayList<>();

    private GameConfigurerImpl gameConfigurer;

    private boolean inProgress;

    private static boolean isEmpty(final String string) {
        return null == string || string.isEmpty();
    }

    void validateCanJoin(final String playerName) {
        if (isEmpty(playerName)) {
            throw new RummikubException(NO_NAME);
        }
        if (players.containsKey(playerName)) {
            throw new RummikubException(NAME_TAKEN);
        }
        if (inProgress) {
            throw new RummikubException(ONGOING_GAME);
        }
    }

    void addPlayer(final String playerName, final StateChangeListener listener) {
        players.put(playerName, listener);
        listener.enteredWaitingRoom(this);
        broadcastPlayerJoined(playerName);
    }

    void addPlayer(final String playerName, final ServerStateChangeListener listener) {
        listener.enteredWaitingRoom(this);
        notifyOfPreviouslyJoinedPlayers(listener);
        players.put(playerName, listener);
        broadcastPlayerJoined(playerName);
    }

    private void notifyOfPreviouslyJoinedPlayers(ServerStateChangeListener listener) {
        for (String playerName : players.keySet()) {
            listener.serverRummikubRoomListener.playerJoined(playerName);
        }
    }

    private void broadcastPlayerJoined(final String playerName) {
        waitingRoomListeners
                .forEach(waitingRoomListener -> waitingRoomListener.playerJoined(playerName));
    }

    private void broadcastPlayerLeft(final String playerName) {
        waitingRoomListeners
                .forEach(waitingRoomListener -> waitingRoomListener.playerLeft(playerName));
    }

    @Override
    public void startGame() {
        gameConfigurer = new GameConfigurerImpl();
        players.values()
                .forEach(listener -> listener.enteredGame(gameConfigurer));
        inProgress = true;
    }

    @Override
    public void registerListener(final RummikubRoomListener listener) {
        waitingRoomListeners.add(listener);
    }

    @Override
    public void unregisterListener(RummikubRoomListener listener) {
        waitingRoomListeners.remove(listener);
    }

    void unregister(final String playerName) {
        players.remove(playerName); // TODO synchronize deez!
        if (!inProgress) {
            broadcastPlayerLeft(playerName);
        }
    }

}