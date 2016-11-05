package com.apixandru.rummikub.connection;

import com.apixandru.rummikub.brotocol.Packet;

import java.util.LinkedList;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

import static com.apixandru.rummikub.connection.TestUtils.getClassNames;
import static com.apixandru.rummikub.connection.TestUtils.safeCast;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Alexandru-Constantin Bledea
 * @since Aug 19, 2016
 */
class MockPacketConnection implements PacketConnection {

    private final LinkedList<Packet> packets = new LinkedList<>();

    private final BlockingDeque<Packet> inputPackets = new LinkedBlockingDeque<>();

    private final AtomicBoolean continueReading = new AtomicBoolean(true);

    @Override
    public boolean trySendPacket(Packet packet) {
        return packets.add(packet);
    }

    @Override
    public Packet readPacket() {
        while (continueReading.get()) {
            try {
                Packet packet = inputPackets.pollFirst(100, MILLISECONDS);
                if (null != packet) {
                    return packet;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void close() {
        continueReading.set(false);
    }

    void assertNextPacket(Consumer<Packet> predicate) {
        assertFalse("Expecting to have more packets but there are none left", packets.isEmpty());
        predicate.accept(packets.removeFirst());
    }

    <P extends Packet> void assertNextPacketInstanceof(Class<P> packetClass) {
        assertNextPacket(packet -> safeCast(packet, packetClass));
    }

    void assertNoOtherPackages() {
        assertTrue("Expecting no other packages, but we have " + getClassNames(packets), packets.isEmpty());
    }

    void assumeIncomingPacket(Packet packet) {
        inputPackets.add(packet);
    }

}
