package com.apixandru.rummikub.server;

import com.apixandru.rummikub.api.room.RummikubRoomListener;
import com.apixandru.rummikub.model.Rummikub;
import com.apixandru.rummikub.model.RummikubFactory;

import java.util.HashMap;
import java.util.LinkedHashMap;
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

    private final Map<String, RummikubRoomListener> waitingRoomListeners = new LinkedHashMap<>();

    private Rummikub<Integer> rummikubGame;

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
        listener.enteredWaitingRoom(this);
        notifyOfPreviouslyJoinedPlayers(playerName);
        players.put(playerName, listener);
        broadcastPlayerJoined(playerName);
    }

    private void notifyOfPreviouslyJoinedPlayers(String newPlayer) {
        RummikubRoomListener listener = waitingRoomListeners.get(newPlayer);
        for (String playerName : players.keySet()) {
            listener.playerJoined(playerName);
        }
    }

    private void broadcastPlayerJoined(final String playerName) {
        waitingRoomListeners
                .values()
                .forEach(waitingRoomListener -> waitingRoomListener.playerJoined(playerName));
    }

    private void broadcastPlayerLeft(final String playerName) {
        waitingRoomListeners
                .values()
                .forEach(waitingRoomListener -> waitingRoomListener.playerLeft(playerName));
    }

    @Override
    public void startGame() {
        rummikubGame = RummikubFactory.newInstance();
        players.values()
                .forEach(listener -> listener.enteredGame(rummikubGame));
        inProgress = true;
    }

    @Override
    public void registerListener(String playerName, final RummikubRoomListener listener) {
        waitingRoomListeners.put(playerName, listener);
    }

    @Override
    public void unregisterListener(RummikubRoomListener listener) {
        waitingRoomListeners
                .values()
                .remove(listener);
    }

    void unregister(final String playerName) {
        players.remove(playerName); // TODO synchronize deez!
        if (!inProgress) {
            broadcastPlayerLeft(playerName);
        }
    }

}
