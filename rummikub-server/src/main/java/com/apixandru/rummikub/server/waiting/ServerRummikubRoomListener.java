package com.apixandru.rummikub.server.waiting;

import com.apixandru.rummikub.api.config.RummikubRoomConfigurer;
import com.apixandru.rummikub.api.room.RummikubRoomListener;
import com.apixandru.rummikub.brotocol.PacketWriter;
import com.apixandru.rummikub.brotocol.connect.server.PacketPlayerJoined;
import com.apixandru.rummikub.brotocol.connect.server.PacketPlayerLeft;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 15, 2016
 */
public class ServerRummikubRoomListener implements RummikubRoomListener {

    private final PacketWriter packetWriter;
    private final RummikubRoomConfigurer configurer;

    public ServerRummikubRoomListener(RummikubRoomConfigurer configurer, final PacketWriter packetWriter) {
        this.packetWriter = packetWriter;
        this.configurer = configurer;
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

    public void cleanup() {
        configurer.unregisterListener(this);
    }

}
