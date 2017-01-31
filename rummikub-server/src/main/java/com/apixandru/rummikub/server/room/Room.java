package com.apixandru.rummikub.server.room;

import com.apixandru.rummikub.api.room.RummikubRoomListener;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Alexandru-Constantin Bledea
 * @since Sep 02, 2016
 */
final class Room {

    private final Map<String, RummikubRoomListener> roomListener = new LinkedHashMap<>();

    private void informAboutPreviouslyJoinedPlayers(RummikubRoomListener roomListener) {
        this.roomListener.keySet()
                .forEach(roomListener::playerJoined);
    }

    void join(String playerName, RummikubRoomListener roomListener) {
        informAboutPreviouslyJoinedPlayers(roomListener);
        this.roomListener.put(playerName, roomListener);
        this.roomListener.values()
                .forEach(listener -> listener.playerJoined(playerName));
    }

    void leave(String playerName) {
        if (null != this.roomListener.remove(playerName)) {
            this.roomListener.values()
                    .forEach(listener -> listener.playerLeft(playerName));
        }
    }

}
