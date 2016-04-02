package com.apixandru.games.rummikub.client.waiting;

import com.apixandru.games.rummikub.brotocol.PacketHandler;
import com.apixandru.games.rummikub.brotocol.connect.WaitingRoomListener;
import com.apixandru.games.rummikub.brotocol.connect.client.PacketStart;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 01, 2016
 */
public class StartGameHandler implements PacketHandler<PacketStart> {

    private final WaitingRoomListener waitingRoomListener;

    public StartGameHandler(final WaitingRoomListener waitingRoomListener) {
        this.waitingRoomListener = waitingRoomListener;
    }

    @Override
    public void handle(final PacketStart packet) {
        this.waitingRoomListener.startGame();
    }

}
