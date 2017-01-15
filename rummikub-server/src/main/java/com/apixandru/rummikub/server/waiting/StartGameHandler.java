package com.apixandru.rummikub.server.waiting;

import com.apixandru.rummikub.api.room.StartGameListener;
import com.apixandru.rummikub.brotocol.PacketHandler;
import com.apixandru.rummikub.brotocol.connect.client.StartGameRequest;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 15, 2017
 */
public class StartGameHandler implements PacketHandler<StartGameRequest> {

    private final StartGameListener startGameListener;

    public StartGameHandler(StartGameListener startGameListener) {
        this.startGameListener = startGameListener;
    }

    @Override
    public void handle(StartGameRequest packet) {
        startGameListener.startGame();
    }

}
