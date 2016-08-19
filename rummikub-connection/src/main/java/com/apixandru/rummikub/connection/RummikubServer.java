package com.apixandru.rummikub.connection;

import com.apixandru.rummikub.connection.packet.ServerShutdownBroadcast;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Alexandru-Constantin Bledea
 * @since Aug 16, 2016
 */
public class RummikubServer implements Runnable, Closeable {

    private static final Logger log = LoggerFactory.getLogger(RummikubServer.class);

    private final ServerSocket serverSocket;

    private final AtomicBoolean continueReading = new AtomicBoolean(true);

    private final List<RummikubConnection> clients = new ArrayList<>();

    public RummikubServer(ServerSocket serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void run() {
        while (continueReading.get()) {
            RummikubConnection socket = tryAccept();
            if (null != socket) {
                clients.add(socket);
            }
        }
        disconnectClients();
    }

    private void disconnectClients() {
        clients.forEach(RummikubServer::disconnectClient);
    }

    private static void disconnectClient(RummikubConnection connection) {
        connection.writePacket(new ServerShutdownBroadcast());
        try {
            connection.close();
        } catch (IOException e) {
            throw new IllegalStateException(e); // ignore this?
        }
    }

    private RummikubConnection tryAccept() {
        while (continueReading.get()) {
            try {
                return new RummikubConnection(serverSocket.accept());
            } catch (SocketTimeoutException timeoutException) {
                // give the server a chance to exit
            } catch (IOException e) {
                log.error("Failed to connect", e);
            }
        }
        return null;
    }

    @Override
    public void close() throws IOException {
        continueReading.set(false);
    }

}
