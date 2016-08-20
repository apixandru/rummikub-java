package com.apixandru.rummikub.connection;

import com.apixandru.rummikub.connection.packet.ServerShutdownBroadcast;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.apixandru.rummikub.connection.util.Util.closeQuietly;

/**
 * @author Alexandru-Constantin Bledea
 * @since Aug 16, 2016
 */
public class RummikubServer implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(RummikubServer.class);

    private final PacketConnector packetConnector;

    private final AtomicBoolean continueReading = new AtomicBoolean(true);

    private final List<PacketConnection> clients = new ArrayList<>();

    public RummikubServer(PacketConnector packetConnector) {
        this.packetConnector = packetConnector;
    }

    private static void disconnectClient(PacketConnection connection) {
        log.debug("Disconnecting client...");
        connection.writePacket(new ServerShutdownBroadcast());
        closeQuietly(connection);
    }

    @Override
    public void run() {
        log.debug("Server started. Waiting for connections on port {}", packetConnector.getPort());
        while (continueReading.get()) {
            PacketConnection connection = tryAccept();
            if (null != connection) {
                accept(connection);
            }
        }
        disconnectClients();
    }

    private void accept(PacketConnection connection) {
        clients.add(connection);
        log.debug("Accepted connection");
    }

    private void disconnectClients() {
        clients.forEach(RummikubServer::disconnectClient);
    }

    private PacketConnection tryAccept() {
        log.debug("Trying to connect...");
        try {
            return packetConnector.acceptConnection();
        } catch (IOException e) {
            log.error("Failed to connect", e);
            return null;
        }
    }

    public void shutdown() {
        log.debug("Shutdown requested.");
        continueReading.set(false);
        packetConnector.stopAccepting();
    }

}
