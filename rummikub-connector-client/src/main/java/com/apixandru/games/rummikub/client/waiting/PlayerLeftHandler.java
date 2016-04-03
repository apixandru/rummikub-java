package com.apixandru.games.rummikub.client.waiting;

import com.apixandru.games.rummikub.brotocol.PacketHandler;
import com.apixandru.games.rummikub.brotocol.connect.WaitingRoomListener;
import com.apixandru.games.rummikub.brotocol.connect.server.PacketPlayerLeft;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 01, 2016
 */
public final class PlayerLeftHandler implements PacketHandler<PacketPlayerLeft> {

    private final WaitingRoomListener waitingRoomListener;

    public PlayerLeftHandler(final WaitingRoomListener waitingRoomListener) {
        this.waitingRoomListener = waitingRoomListener;
    }

    @Override
    public void handle(final PacketPlayerLeft packet) {
        waitingRoomListener.playerLeft(packet.playerName);
    }

}
