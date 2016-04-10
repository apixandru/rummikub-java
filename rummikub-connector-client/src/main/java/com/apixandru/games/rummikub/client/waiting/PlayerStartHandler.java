package com.apixandru.games.rummikub.client.waiting;

import com.apixandru.games.rummikub.brotocol.PacketHandler;
import com.apixandru.games.rummikub.brotocol.connect.client.PacketStart;
import com.apixandru.rummikub.StateChangeListener;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 01, 2016
 */
public class PlayerStartHandler<H> implements PacketHandler<PacketStart> {

    private final StateChangeListener<H> waitingRoomListeners;

    public PlayerStartHandler(final StateChangeListener<H> stateChangeListener) {
        this.waitingRoomListeners = stateChangeListener;
    }

    @Override
    public void handle(final PacketStart packet) {
        waitingRoomListeners.enteredGame(null);
    }

}
