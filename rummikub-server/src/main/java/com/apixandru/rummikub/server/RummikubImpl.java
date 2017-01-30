package com.apixandru.rummikub.server;

import com.apixandru.rummikub.api.game.GameEventListener;
import com.apixandru.rummikub.api.game.GameOverReason;
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
        players.put(playerName, listener);

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

        rummikubGame.addGameEventListener(new RummikubServerGameEventListener());

        inProgress = true;
    }

    @Override
    public void registerListener(String playerName, final RummikubRoomListener listener) {
        notifyOfPreviouslyJoinedPlayers(listener);
        waitingRoomListeners.put(playerName, listener);
        broadcastPlayerJoined(playerName);
    }

    private void notifyOfPreviouslyJoinedPlayers(RummikubRoomListener listener) {
        for (String previouslyJoinedPlayerName : waitingRoomListeners.keySet()) {
            listener.playerJoined(previouslyJoinedPlayerName);
        }
    }

    @Override
    public void unregisterListener(String playerName) {
        waitingRoomListeners.remove(playerName);
        broadcastPlayerLeft(playerName);
    }

    void unregister(final String playerName) {
        players.remove(playerName); // TODO synchronize deez!
    }

    private class RummikubServerGameEventListener implements GameEventListener {

        @Override
        public void newTurn(String player) {
        }

        @Override
        public void gameOver(String player, GameOverReason reason) {
            players.values()
                    .forEach(listener -> listener.enteredWaitingRoom(RummikubImpl.this));
        }

    }

}
