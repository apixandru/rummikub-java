package com.apixandru.rummikub.client.waiting;

import com.apixandru.rummikub.api.room.StartGameListener;
import com.apixandru.rummikub.brotocol.PacketHandler;
import com.apixandru.rummikub.brotocol.connect.server.PacketPlayerStart;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 01, 2016
 */
public class PlayerStartHandler implements PacketHandler<PacketPlayerStart> {

    private final StartGameListener stateChangeListener;

    public PlayerStartHandler(final StartGameListener stateChangeListener) {
        this.stateChangeListener = stateChangeListener;
    }

    @Override
    public void handle(final PacketPlayerStart packet) {
        stateChangeListener.startGame();
    }

}
