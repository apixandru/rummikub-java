package com.apixandru.games.rummikub.server.waiting;

import com.apixandru.games.rummikub.brotocol.PacketHandler;
import com.apixandru.games.rummikub.brotocol.connect.client.PacketStart;
import com.apixandru.games.rummikub.brotocol.util.Reference;
import com.apixandru.rummikub.model.waiting.StartGameListener;

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
