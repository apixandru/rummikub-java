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
    private RummikubRoomConfigurer configurer;

    public ServerRummikubRoomListener(final PacketWriter packetWriter) {
        this.packetWriter = packetWriter;
    }

    public void setConfigurer(RummikubRoomConfigurer configurer) {
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
        if (null != configurer) {
            configurer.unregisterListener(this);
        }
        configurer = null;
    }

}
