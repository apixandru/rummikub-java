package com.apixandru.rummikub.client.waiting;

import com.apixandru.rummikub.api.room.RummikubRoomListener;
import com.apixandru.rummikub.brotocol.PacketWriter;
import com.apixandru.rummikub.brotocol.connect.client.PacketStart;
import com.apixandru.rummikub.client.ClientPacketHandler;

/**
 * @author Alexandru-Constantin Bledea
 * @since Apr 17, 2016
 */
public class ClientWaitingRoomConfigurer implements RummikubRoomConfigurer {

    private final PacketWriter packetWriter;
    private final ClientPacketHandler packetHandler;

    public ClientWaitingRoomConfigurer(final ClientPacketHandler packetHandler, final PacketWriter packetWriter) {
        this.packetHandler = packetHandler;
        this.packetWriter = packetWriter;
    }

    @Override
    public void registerListener(final RummikubRoomListener listener) {
        packetHandler.setWaitingRoomListener(listener);
    }

    @Override
    public void unregisterListener(RummikubRoomListener listener) {
        packetHandler.setWaitingRoomListener(null);
    }

    @Override
    public void startGame() {
        packetWriter.writePacket(new PacketStart());
    }

}
