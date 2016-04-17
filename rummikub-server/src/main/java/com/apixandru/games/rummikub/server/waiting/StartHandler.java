package com.apixandru.games.rummikub.server.waiting;

import com.apixandru.games.rummikub.brotocol.PacketHandler;
import com.apixandru.games.rummikub.brotocol.connect.client.PacketStart;
import com.apixandru.rummikub.server.Reference;
import com.apixandru.rummikub.waiting.StartGameListener;

/**
 * @author Alexandru-Constantin Bledea
 * @since Apr 17, 2016
 */
public class StartHandler implements PacketHandler<PacketStart> {

    private final Reference<StartGameListener> startGameListener;

    public StartHandler(Reference<StartGameListener> startGameListener) {
        this.startGameListener = startGameListener;
    }

    @Override
    public void handle(PacketStart packet) {
        startGameListener.get().startGame();
    }

}
