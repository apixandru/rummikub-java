package com.apixandru.games.rummikub.client.waiting;

import com.apixandru.games.rummikub.brotocol.PacketWriter;
import com.apixandru.games.rummikub.brotocol.connect.client.PacketStart;
import com.apixandru.rummikub.waiting.StartGameListener;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 02, 2016
 */
public class StartGameListenerImpl implements StartGameListener {

    private final PacketWriter packetWriter;

    public StartGameListenerImpl(final PacketWriter packetWriter) {
        this.packetWriter = packetWriter;
    }

    @Override
    public void startGame() {
        this.packetWriter.writePacket(new PacketStart());
    }

}
