package com.apixandru.rummikub.server.waiting;

import com.apixandru.rummikub.brotocol.PacketWriter;
import com.apixandru.rummikub.brotocol.connect.server.PacketPlayerJoined;
import com.apixandru.rummikub.brotocol.connect.server.PacketPlayerLeft;
import com.apixandru.rummikub.brotocol.room.RummikubRoomListener;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 15, 2016
 */
public class ServerRummikubRoomListener implements RummikubRoomListener {

    private final PacketWriter packetWriter;

    public ServerRummikubRoomListener(final PacketWriter packetWriter) {
        this.packetWriter = packetWriter;
    }

    @Override
    public void playerJoined(final String playerName) {
        final PacketPlayerJoined packet = new PacketPlayerJoined();
        packet.playerName = playerName;
        packetWriter.writePacket(packet);
    }

    @Override
    public void playerLeft(final String playerName) {
        final PacketPlayerLeft packet = new PacketPlayerLeft();
        packet.playerName = playerName;
        packetWriter.writePacket(packet);
    }

}
