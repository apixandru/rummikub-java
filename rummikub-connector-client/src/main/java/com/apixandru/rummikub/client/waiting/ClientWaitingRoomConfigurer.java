package com.apixandru.rummikub.client.waiting;

import com.apixandru.games.rummikub.brotocol.PacketWriter;
import com.apixandru.games.rummikub.brotocol.connect.client.PacketStart;
import com.apixandru.rummikub.client.ClientPacketHandler;
import com.apixandru.rummikub.waiting.WaitingRoomConfigurer;
import com.apixandru.rummikub.waiting.WaitingRoomListener;

/**
 * @author Alexandru-Constantin Bledea
 * @since Apr 17, 2016
 */
public class ClientWaitingRoomConfigurer implements WaitingRoomConfigurer {

    private final PacketWriter packetWriter;
    private final ClientPacketHandler packetHandler;

    public ClientWaitingRoomConfigurer(final PacketWriter packetWriter, final ClientPacketHandler packetHandler) {
        this.packetWriter = packetWriter;
        this.packetHandler = packetHandler;
    }

    @Override
    public void registerListener(final WaitingRoomListener listener) {
        packetHandler.setWaitingRoomListener(listener);
    }

    @Override
    public void startGame() {
        packetWriter.writePacket(new PacketStart());
    }

}
