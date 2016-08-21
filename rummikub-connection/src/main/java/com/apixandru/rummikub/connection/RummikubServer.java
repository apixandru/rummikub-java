package com.apixandru.rummikub.connection;

import com.apixandru.rummikub.connection.packet.ConnectionResponse;
import com.apixandru.rummikub.connection.packet.ServerShutdownBroadcast;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.apixandru.rummikub.connection.packet.ReasonCode.REACHED_MAXIMUM_NUMBER_OF_CONNECTIONS;

/**
 * @author Alexandru-Constantin Bledea
 * @since Aug 16, 2016
 */
public class RummikubServer implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(RummikubServer.class);

    private final PacketConnector packetConnector;

    private final AtomicBoolean continueReading = new AtomicBoolean(true);

    private final List<PacketConnection> clients = new ArrayList<>();

    private int maximumConnections = 10;

    public RummikubServer(PacketConnector packetConnector) {
        this.packetConnector = packetConnector;
    }

    private static void disconnectClient(PacketConnection connection) {
        log.debug("Disconnecting client...");
        if (connection.trySendPacket(new ServerShutdownBroadcast())) {
            connection.close();
        }
    }

    private static void reject(PacketConnection connection) {
        log.debug("Rejected connection");
        if (connection.trySendPacket(new ConnectionResponse(false, REACHED_MAXIMUM_NUMBER_OF_CONNECTIONS))) {
            connection.close();
        }
    }

    public void setMaximumConnections(int maximumConnections) {
        this.maximumConnections = maximumConnections;
    }

    public int getAvailableNumberOfConnections() {
        return maximumConnections - clients.size();
    }

    public boolean hasAvailableConnection() {
        return getAvailableNumberOfConnections() > 0;
    }

    @Override
    public void run() {
        log.debug("Server started. Waiting for connections on port {}", packetConnector.getPort());
        while (continueReading.get()) {
            PacketConnection connection = tryAccept();
            if (null != connection) {
                handle(connection);
            }
        }
        disconnectClients();
    }

    private void handle(PacketConnection connection) {
        if (hasAvailableConnection()) {
            accept(connection);
        } else {
            reject(connection);
        }

    }

    private void accept(PacketConnection connection) {
        if (connection.trySendPacket(new ConnectionResponse(true, null))) {
            clients.add(connection);
            log.debug("Accepted connection");
        }
    }

    private void disconnectClients() {
        Iterator<PacketConnection> it = clients.iterator();
        while (it.hasNext()) {
            PacketConnection client = it.next();
            it.remove();
            disconnectClient(client);
        }
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
