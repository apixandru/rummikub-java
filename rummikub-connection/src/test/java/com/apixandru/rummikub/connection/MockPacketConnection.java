package com.apixandru.rummikub.connection;

import com.apixandru.rummikub.brotocol.Packet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexandru-Constantin Bledea
 * @since Aug 19, 2016
 */
public class MockPacketConnection implements PacketConnection {

    private final List<Packet> packets = new ArrayList<>();

    @Override
    public void writePacket(Packet packet) {
        packets.add(packet);
    }

    @Override
    public Packet readPacket() throws IOException {
        return null;
    }

    @Override
    public void close() throws IOException {

    }

}
