package com.apixandru.games.rummikub.swing.waiting;

import com.apixandru.games.rummikub.brotocol.PacketHandler;
import com.apixandru.games.rummikub.brotocol.connect.WaitingRoomListener;
import com.apixandru.games.rummikub.brotocol.connect.server.PacketPlayerJoined;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 01, 2016
 */
final class PlayerJoinedHandler implements PacketHandler<PacketPlayerJoined> {

    private final WaitingRoomListener waitingRoomListener;

    PlayerJoinedHandler(final WaitingRoomListener waitingRoomListener) {
        this.waitingRoomListener = waitingRoomListener;
    }

    @Override
    public void handle(final PacketPlayerJoined packet) {
        waitingRoomListener.playerJoined(packet.playerName);
    }

}
