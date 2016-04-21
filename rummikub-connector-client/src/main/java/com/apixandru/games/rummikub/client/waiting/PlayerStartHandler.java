package com.apixandru.games.rummikub.client.waiting;

import com.apixandru.games.rummikub.brotocol.PacketHandler;
import com.apixandru.games.rummikub.brotocol.connect.server.PacketPlayerStart;
import com.apixandru.games.rummikub.brotocol.util.Reference;
import com.apixandru.rummikub.waiting.StartGameListener;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 01, 2016
 */
public class PlayerStartHandler<H> implements PacketHandler<PacketPlayerStart> {

    private final Reference<StartGameListener> stateChangeListener;

    public PlayerStartHandler(final Reference<StartGameListener> stateChangeListener) {
        this.stateChangeListener = stateChangeListener;
    }

    @Override
    public void handle(final PacketPlayerStart packet) {
        stateChangeListener.get().startGame();
    }

}
