package com.apixandru.rummikub.connection;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static com.apixandru.rummikub.connection.TestUtils.safeSleep;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * @author Alexandru-Constantin Bledea
 * @since Aug 19, 2016
 */
class MockPacketConnector implements PacketConnector {

    private final AtomicBoolean continueAccepting = new AtomicBoolean(true);

    private final Queue<PacketConnection> packetConnections = new ConcurrentLinkedDeque<>();

    private final AtomicInteger connectionCounter = new AtomicInteger();

    @Override
    public PacketConnection acceptConnection() {
        while (continueAccepting.get()) {
            PacketConnection connection = packetConnections.poll();
            if (null != connection) {
                connectionCounter.incrementAndGet();
                return connection;
            }
            safeSleep(100, MILLISECONDS);
        }
        return null;
    }

    @Override
    public void stopAccepting() {
        continueAccepting.set(false);
    }

    void assumeConnection(PacketConnection packetConnection) {
        this.packetConnections.add(packetConnection);
    }

    int getNumberOfAcceptedClients() {
        return connectionCounter.get();
    }

}
