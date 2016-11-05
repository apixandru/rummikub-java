package com.apixandru.rummikub.connection;

import com.apixandru.rummikub.brotocol.Packet;
import com.apixandru.rummikub.brotocol2.ConnectionListener;
import com.apixandru.rummikub.connection.packet.ServerShutdownBroadcast;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Alexandru-Constantin Bledea
 * @since Aug 20, 2016
 */
class ClientConnectionRunnable implements Runnable, ConnectionListener {

    private final AtomicBoolean continueReading = new AtomicBoolean(true);

    private final PacketConnection connection;
    private final ServerConnectorPacketHandler connectorPacketHandler;

    ClientConnectionRunnable(PacketConnection connection, ServerConnectorPacketHandler connectorPacketHandler) {
        this.connection = connection;
        this.connectorPacketHandler = connectorPacketHandler;
    }

    @Override
    public void run() {
        while (continueReading.get()) {
            Packet packet = connection.readPacket();
            connectorPacketHandler.handle(packet);
        }
    }

    @Override
    public void connectionCloseRequest() {
        continueReading.set(false);
        if (connection.trySendPacket(new ServerShutdownBroadcast())) {
            connection.close();
        }
    }

    ServerConnectorPacketHandler getConnectorPacketHandler() {
        return connectorPacketHandler;
    }

}
