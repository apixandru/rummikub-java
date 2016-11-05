package com.apixandru.rummikub.connection;

import com.apixandru.rummikub.brotocol.Packet;
import com.apixandru.rummikub.connection.packet.ConnectionResponse;
import com.apixandru.rummikub.connection.packet.ReasonCode;
import com.apixandru.rummikub.connection.packet.ServerShutdownBroadcast;

import static com.apixandru.rummikub.connection.TestUtils.safeCast;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Alexandru-Constantin Bledea
 * @since Aug 20, 2016
 */
final class Packets {

    private Packets() {
    }

    private static void assertAcceptedConnection(Packet packet) {
        ConnectionResponse connectionResponse = safeCast(packet, ConnectionResponse.class);
        assertTrue("Expected connection to be accepted", connectionResponse.accepted);
    }

    static void assertAcceptedThenShutdown(MockPacketConnection... connections) {
        for (MockPacketConnection connection : connections) {
            connection.assertNextPacket(Packets::assertAcceptedConnection);
            connection.assertNextPacketInstanceof(ServerShutdownBroadcast.class);
            connection.assertNoOtherPackages();
        }
    }

    static void assertConnectionRejected(Packet packet, ReasonCode reasonCode) {
        ConnectionResponse connectionResponse = safeCast(packet, ConnectionResponse.class);
        assertFalse("Expected connection to be rejected", connectionResponse.accepted);
        assertThat(connectionResponse.reasonCode)
                .isEqualTo(reasonCode);
    }

}
