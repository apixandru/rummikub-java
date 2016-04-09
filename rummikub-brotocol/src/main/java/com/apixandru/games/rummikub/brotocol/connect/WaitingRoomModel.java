package com.apixandru.games.rummikub.brotocol.connect;

import com.apixandru.rummikub.waiting.WaitingRoomListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 02, 2016
 */
public final class WaitingRoomModel {

    private final List<WaitingRoomListener> listeners = new ArrayList<>();

    public void addWaitingRoomListener(final WaitingRoomListener waitingRoomListener) {
        this.listeners.add(waitingRoomListener);
    }

    public void removeWaitingRoomListener(final WaitingRoomListener waitingRoomListener) {
        this.listeners.remove(waitingRoomListener);
    }

    public void playerJoined(final String playerName) {
        this.listeners.forEach(listener -> listener.playerJoined(playerName));
    }

    public void playerLeft(final String playerName) {
        this.listeners.forEach(listener -> listener.playerLeft(playerName));
    }

}
