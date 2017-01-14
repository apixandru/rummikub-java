package com.apixandru.rummikub.connection;

import com.apixandru.rummikub.connection.packet.client.ExitRequest;
import org.junit.Test;

import static com.apixandru.rummikub.connection.Packets.assertAcceptedThenShutdown;
import static com.apixandru.rummikub.connection.Packets.assertConnectionRejected;
import static com.apixandru.rummikub.connection.TestUtils.awaitCompletion1Second;
import static com.apixandru.rummikub.connection.TestUtils.launchThread;
import static com.apixandru.rummikub.connection.TestUtils.safeSleep;
import static com.apixandru.rummikub.connection.packet.ReasonCode.REACHED_MAXIMUM_NUMBER_OF_CONNECTIONS;
import static java.util.Arrays.stream;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Alexandru-Constantin Bledea
 * @since Aug 19, 2016
 */
public class RummikubServerTest {

    private static MockPacketConnector createConnector(MockPacketConnection... connections) {
        MockPacketConnector packetConnector = new MockPacketConnector();
        stream(connections)
                .forEach(packetConnector::assumeConnection);
        return packetConnector;
    }

    @Test(timeout = 5000L)
    public void testNumberOfConnections() {
        MockPacketConnector connector = new MockPacketConnector();

        RummikubServer server = new RummikubServer(connector);
        server.setMaximumConnections(1);

        Thread thread = launchThread(server);

        assertThat(server.getAvailableNumberOfConnections())
                .isEqualTo(1);

        MockPacketConnection packetConnection = new MockPacketConnection();
        connector.assumeConnection(packetConnection);

        safeSleep(200, MILLISECONDS);

        assertThat(server.getAvailableNumberOfConnections())
                .isEqualTo(0);

        packetConnection.assumeIncomingPacket(new ExitRequest());
//
//        assertThat(server.getAvailableNumberOfConnections())
//                .isEqualTo(1);


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

        awaitCompletion1Second(thread, rummikubServer);

        denied1.assertNextPacket(packet -> assertConnectionRejected(packet, REACHED_MAXIMUM_NUMBER_OF_CONNECTIONS));
        denied1.assertNoOtherPackages();

        assertAcceptedThenShutdown(accept1, accept2);
    }

    @Test(timeout = 5000L)
    public void testShutdown() {
        RummikubServer rummikubServer = new RummikubServer(new MockPacketConnector());
        Thread thread = launchThread(rummikubServer);

        rummikubServer.shutdown();

        awaitCompletion1Second(thread, rummikubServer);
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

        awaitCompletion1Second(thread, rummikubServer);

        assertAcceptedThenShutdown(con1, con2);
    }

}
