package com.apixandru.rummikub.connection;

import com.apixandru.rummikub.brotocol.Packet;
import com.apixandru.rummikub.brotocol2.ConnectionListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Alexandru-Constantin Bledea
 * @since Aug 20, 2016
 */
public class ClientConnectionRunnable implements Runnable, ConnectionListener {

    private static final Logger log = LoggerFactory.getLogger(ClientConnectionRunnable.class);

    private final AtomicBoolean continueReading = new AtomicBoolean(true);

    private final PacketConnection connection;
    private final ServerConnectorPacketHandler connectorPacketHandler;

    public ClientConnectionRunnable(PacketConnection connection, ServerConnectorPacketHandler connectorPacketHandler) {
        this.connection = connection;
        this.connectorPacketHandler = connectorPacketHandler;
    }

    @Override
    public void run() {
        while (continueReading.get()) {
            try {
                Packet packet = connection.readPacket();
                connectorPacketHandler.handle(packet);
            } catch (ConnectionLostException e) {
                log.error("Connection with client broken.", e);
                connectionLost();
            }
        }
    }

    @Override
    public void connectionLost() {
        continueReading.set(false);
    }

    @Override
    public void connectionCloseRequest() {
        continueReading.set(false);
    }
}
