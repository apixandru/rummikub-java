package com.apixandru.rummikub.connection;

import com.apixandru.rummikub.connection.packet.ConnectionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.apixandru.rummikub.connection.packet.ReasonCode.REACHED_MAXIMUM_NUMBER_OF_CONNECTIONS;
import static java.util.Collections.unmodifiableMap;

/**
 * @author Alexandru-Constantin Bledea
 * @since Aug 16, 2016
 */
public class RummikubServer implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(RummikubServer.class);

    private final PacketConnector packetConnector;

    private final AtomicBoolean continueReading = new AtomicBoolean(true);

    private final Map<PacketConnection, ClientConnectionRunnable> clients = new LinkedHashMap<>();
    private final Map<PacketConnection, Thread> clientThreads = new LinkedHashMap<>();

    private int maximumConnections = 10;

    public RummikubServer(PacketConnector packetConnector) {
        this.packetConnector = packetConnector;
    }

    private static void disconnectClient(ClientConnectionRunnable connection) {
        log.debug("Disconnecting client...");
        connection.getConnectorPacketHandler().connectionCloseRequest();
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
            ServerConnectorPacketHandler connectorPacketHandler = new ServerConnectorPacketHandler();
            ClientConnectionRunnable runnable = new ClientConnectionRunnable(connection, connectorPacketHandler);
            connectorPacketHandler.addConnectionListener(runnable);
            clients.put(connection, runnable);
            log.debug("Accepted connection");
            launchClientThread(connection);
        }
    }

    private void launchClientThread(PacketConnection connection) {
        ClientConnectionRunnable runnable = this.clients.get(connection);
        Thread thread = new Thread(runnable);
        clientThreads.put(connection, thread);
        thread.start();
    }

    private void disconnectClients() {
        Iterator<ClientConnectionRunnable> it = clients.values().iterator();
        while (it.hasNext()) {
            ClientConnectionRunnable client = it.next();
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

    Map<PacketConnection, Thread> getClientThreads() {
        return unmodifiableMap(clientThreads);
    }

}
