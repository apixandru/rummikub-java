package com.apixandru.rummikub.server.waiting;

import com.apixandru.rummikub.api.room.StartGameListener;
import com.apixandru.rummikub.brotocol.PacketHandler;
import com.apixandru.rummikub.brotocol.connect.client.PacketStart;
import com.apixandru.rummikub.brotocol.util.Reference;

/**
 * @author Alexandru-Constantin Bledea
 * @since Apr 17, 2016
 */
public class StartHandler implements PacketHandler<PacketStart> {

    private final Reference<StartGameListener> startGameListenerProvider;

    public StartHandler(Reference<StartGameListener> startGameListenerProvider) {
        this.startGameListenerProvider = startGameListenerProvider;
    }

    @Override
    public void handle(PacketStart packet) {
        startGameListenerProvider.get().startGame();
    }

}
