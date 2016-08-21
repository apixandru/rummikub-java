package com.apixandru.rummikub.connection;

import com.apixandru.rummikub.brotocol.Packet;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.function.Consumer;

import static com.apixandru.rummikub.connection.TestUtils.getClassNames;
import static com.apixandru.rummikub.connection.TestUtils.safeCast;
import static java.util.Collections.unmodifiableList;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Alexandru-Constantin Bledea
 * @since Aug 19, 2016
 */
public class MockPacketConnection implements PacketConnection {

    private final LinkedList<Packet> packets = new LinkedList<>();

    private final Queue<Packet> inputPackets = new ConcurrentLinkedDeque<>();

    @Override
    public boolean trySendPacket(Packet packet) {
        return packets.add(packet);
    }

    @Override
    public Packet readPacket() {
        return inputPackets.poll();
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
        assertNextPacket(packet -> safeCast(packet, packetClass));
    }

    public void assertNoOtherPackages() {
        assertTrue("Expecting no other packages, but we have " + getClassNames(packets), packets.isEmpty());
    }

    public void assumeIncomingPacket(Packet packet) {
        inputPackets.add(packet);
    }

}
