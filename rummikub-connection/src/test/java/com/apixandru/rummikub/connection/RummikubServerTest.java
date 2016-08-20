package com.apixandru.rummikub.connection;

import org.junit.Test;

import static com.apixandru.rummikub.connection.Packets.assertAcceptedThenShutdown;
import static com.apixandru.rummikub.connection.Packets.assertConnectionRejected;
import static com.apixandru.rummikub.connection.TestUtils.awaitCompletion;
import static com.apixandru.rummikub.connection.TestUtils.launchThread;
import static com.apixandru.rummikub.connection.TestUtils.safeSleep;
import static com.apixandru.rummikub.connection.packet.ReasonCode.REACHED_MAXIMUM_NUMBER_OF_CONNECTIONS;
import static java.util.Arrays.stream;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Alexandru-Constantin Bledea
 * @since Aug 19, 2016
 */
public class RummikubServerTest {

    private static MockPacketConnector createConnector(MockPacketConnection... conectors) {
        MockPacketConnector packetConnector = new MockPacketConnector();
        stream(conectors)
                .forEach(packetConnector::assumeConnection);
        return packetConnector;
    }

    private static void awaitCompletion1Second(Thread thread) {
        awaitCompletion(thread, 1, SECONDS);
    }

    @Test(timeout = 5000L)
    public void testTooManyConnections() {
        MockPacketConnection accept1 = new MockPacketConnection();
        MockPacketConnection accept2 = new MockPacketConnection();
        MockPacketConnection denied1 = new MockPacketConnection();

        MockPacketConnector connector = createConnector(accept1, accept2, denied1);
        RummikubServer rummikubServer = new RummikubServer(connector);
        rummikubServer.setMaximumConnections(2);

        Thread thread = launchThread(rummikubServer);
        safeSleep(100, MILLISECONDS);
        rummikubServer.shutdown();

        awaitCompletion1Second(thread);

        denied1.assertNextPacket(packet -> assertConnectionRejected(packet, REACHED_MAXIMUM_NUMBER_OF_CONNECTIONS));
        denied1.assertNoOtherPackages();

        assertAcceptedThenShutdown(accept1, accept2);
    }

    @Test(timeout = 5000L)
    public void testShutdown() {
        RummikubServer rummikubServer = new RummikubServer(new MockPacketConnector());
        Thread thread = launchThread(rummikubServer);

        rummikubServer.shutdown();

        awaitCompletion1Second(thread);
    }

    @Test(timeout = 5000L)
    public void testShutdownReceivedShutdownBroadcast() {
        MockPacketConnection con1 = new MockPacketConnection();
        MockPacketConnection con2 = new MockPacketConnection();

        MockPacketConnector connector = createConnector(con1, con2);
        RummikubServer rummikubServer = new RummikubServer(connector);

        Thread thread = launchThread(rummikubServer);
        safeSleep(100, MILLISECONDS);

        rummikubServer.shutdown();


        assertThat(connector.getNumberOfAcceptedClients())
                .isEqualTo(2);

        awaitCompletion1Second(thread);

        assertAcceptedThenShutdown(con1, con2);
    }

}
