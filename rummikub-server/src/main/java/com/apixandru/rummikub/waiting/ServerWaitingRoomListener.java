package com.apixandru.rummikub.waiting;

import com.apixandru.games.rummikub.brotocol.PacketWriter;
import com.apixandru.games.rummikub.brotocol.connect.server.PacketPlayerJoined;
import com.apixandru.games.rummikub.brotocol.connect.server.PacketPlayerLeft;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 15, 2016
 */
public class ServerWaitingRoomListener implements WaitingRoomListener {

    private final PacketWriter socketWrapper;

    public ServerWaitingRoomListener(final PacketWriter socketWrapper) {
        this.socketWrapper = socketWrapper;
    }

    @Override
    public void playerJoined(final String playerName) {
        final PacketPlayerJoined packet = new PacketPlayerJoined();
        packet.playerName = playerName;
        socketWrapper.writePacket(packet);
    }

    @Override
    public void playerLeft(final String playerName) {
        final PacketPlayerLeft packet = new PacketPlayerLeft();
        packet.playerName = playerName;
        socketWrapper.writePacket(packet);
    }

}
