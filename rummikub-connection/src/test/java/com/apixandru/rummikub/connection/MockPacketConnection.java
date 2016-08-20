package com.apixandru.rummikub.connection;

import com.apixandru.rummikub.brotocol.Packet;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

import static com.apixandru.rummikub.connection.TestUtils.getClassNames;
import static java.util.Collections.unmodifiableList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Alexandru-Constantin Bledea
 * @since Aug 19, 2016
 */
public class MockPacketConnection implements PacketConnection {

    private final LinkedList<Packet> packets = new LinkedList<>();

    @Override
    public void writePacket(Packet packet) {
        packets.add(packet);
    }

    @Override
    public Packet readPacket() throws IOException {
        return null;
    }

    @Override
    public void close() {

    }

    public List<Packet> getPacketsSent() {
        return unmodifiableList(packets);
    }

    public void assertNextPacket(Consumer<Packet> predicate) {
        assertFalse("Expecting to have more packets but there are none left", packets.isEmpty());
        predicate.accept(packets.removeFirst());
    }

    public <P extends Packet> void assertNextPacketInstanceof(Class<P> packetClass) {
        assertNextPacket(packet -> assertEquals("Wrong class", packetClass, packet.getClass()));
    }

    public void assertNoOtherPackages() {
        assertTrue("Expecting no other packages, but we have " + getClassNames(packets), packets.isEmpty());
    }

}
