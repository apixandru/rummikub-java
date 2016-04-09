package com.apixandru.games.rummikub.client.waiting;

import com.apixandru.games.rummikub.brotocol.PacketHandler;
import com.apixandru.games.rummikub.brotocol.connect.client.PacketStart;
import com.apixandru.rummikub.waiting.StartGameListener;

import java.util.List;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 01, 2016
 */
public class PlayerStartHandler implements PacketHandler<PacketStart> {

    private final List<StartGameListener> waitingRoomListeners;

    public PlayerStartHandler(final List<StartGameListener> waitingRoomListeners) {
        this.waitingRoomListeners = waitingRoomListeners;
    }

    @Override
    public void handle(final PacketStart packet) {
        this.waitingRoomListeners.forEach(StartGameListener::startGame);
    }

}
