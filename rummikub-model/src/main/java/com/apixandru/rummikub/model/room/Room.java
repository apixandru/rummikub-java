package com.apixandru.rummikub.model.room;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;

/**
 * @author Alexandru-Constantin Bledea
 * @since Sep 02, 2016
 */
public final class Room {

    private final Collection<String> players = new LinkedHashSet<>();

    private final List<RoomListener> roomListeners = new ArrayList<>();

    public void addRoomListener(RoomListener RoomListener) {
        this.roomListeners.add(RoomListener);
    }

    public void removeRoomListener(RoomListener RoomListener) {
        this.roomListeners.remove(RoomListener);
    }

    public boolean join(String playerName) {
        if (this.players.add(playerName)) {
            this.roomListeners.forEach(listener -> listener.playerJoined(playerName));
            return true;
        }
        return false;
    }

    public boolean leave(String playerName) {
        if (this.players.remove(playerName)) {
            this.roomListeners.forEach(listener -> listener.playerLeft(playerName));
            return true;
        }
        return false;
    }

}
