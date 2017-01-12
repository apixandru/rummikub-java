package com.apixandru.rummikub.server.room;

import com.apixandru.rummikub.api.room.RummikubRoomListener;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Alexandru-Constantin Bledea
 * @since Sep 02, 2016
 */
final class Room {

    private final List<RummikubRoomListener> roomListeners = new ArrayList<>();

    private final Map<String, RummikubRoomListener> listeners = new LinkedHashMap<>();

    @Deprecated
    void addRoomListener(RummikubRoomListener roomListener) {
        informAboutPreviouslyJoinedPlayers(roomListener);
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
        this.listeners.put(playerName, roomListener);
        this.listeners.values()
                .forEach(listener -> listener.playerJoined(playerName));
    }

    boolean leave(String playerName) {
        if (!this.listeners.containsKey(playerName)) {
            return false;
        }
        this.listeners.values()
                .forEach(listener -> listener.playerLeft(playerName));
        this.listeners.remove(playerName);
        return true;
    }

}
