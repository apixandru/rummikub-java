package com.apixandru.rummikub.client.waiting;

import com.apixandru.rummikub.api.StartGameListener;
import com.apixandru.rummikub.brotocol.PacketHandler;
import com.apixandru.rummikub.brotocol.connect.server.PacketPlayerStart;
import com.apixandru.rummikub.brotocol.util.Reference;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 01, 2016
 */
public class PlayerStartHandler implements PacketHandler<PacketPlayerStart> {

    private final Reference<StartGameListener> stateChangeListener;

    public PlayerStartHandler(final Reference<StartGameListener> stateChangeListener) {
        this.stateChangeListener = stateChangeListener;
    }

    @Override
    public void handle(final PacketPlayerStart packet) {
        stateChangeListener.get().startGame();
    }

}
