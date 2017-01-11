package com.apixandru.rummikub.server.room;

import com.apixandru.rummikub.api.room.RummikubRoomListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

/**
 * @author Alexandru-Constantin Bledea
 * @since Sep 02, 2016
 */
final class Room {

    private final Collection<String> players = new LinkedHashSet<>();

    private final List<RummikubRoomListener> roomListeners = new ArrayList<>();

    private final Map<String, RummikubRoomListener> listeners = new LinkedHashMap<>();

    @Deprecated
    void addRoomListener(RummikubRoomListener roomListener) {
        players.forEach(roomListener::playerJoined);
        this.roomListeners.add(roomListener);
    }

    private void informAboutPreviouslyJoinedPlayers(RummikubRoomListener roomListener) {
        listeners.keySet()
                .forEach(roomListener::playerJoined);
    }

    void removeRoomListener(RummikubRoomListener roomListener) {
        this.roomListeners.remove(roomListener);
    }

    void join(String playerName, RummikubRoomListener roomListener) {
        informAboutPreviouslyJoinedPlayers(roomListener);
        this.players.add(playerName);
        this.listeners.values()
                .forEach(listener -> listener.playerJoined(playerName));
    }

    @Deprecated
    boolean join(String playerName) {
        if (this.players.add(playerName)) {
            this.roomListeners.forEach(listener -> listener.playerJoined(playerName));
            return true;
        }
        return false;
    }

    boolean leave(String playerName) {
        if (this.players.remove(playerName)) {
            this.roomListeners.forEach(listener -> listener.playerLeft(playerName));
            return true;
        }
        return false;
    }

}
