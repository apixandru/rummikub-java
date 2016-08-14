package com.apixandru.rummikub.server;

import com.apixandru.rummikub.brotocol.SocketWrapper;
import com.apixandru.rummikub.brotocol2.Connector;
import com.apixandru.rummikub.brotocol2.ServerSocketConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 04, 2016
 */
class RummikubServer implements Runnable {

    private static final Logger log = LoggerFactory.getLogger(RummikubServer.class);

    private final Connector connector;

    RummikubServer(Connector connector) {
        this.connector = connector;
        log.debug("Listening on port {}", connector.getPort());
    }

    public static void main(String[] args) throws IOException {

        final ServerSocket serverSocket = new ServerSocket(50122);
        ServerSocketConnector connector = new ServerSocketConnector(serverSocket);
        RummikubServer rummikubServer = new RummikubServer(connector);
        new Thread(rummikubServer).start();
    }

    @Override
    public void run() {
        final ConnectionHandler joiner = new ConnectionHandler();
        while (true) {
            log.debug("Waiting for client...");
            joiner.attemptToJoin(newConnection());
        }

    }

    private SocketWrapper newConnection() {
        try {
            return new SocketWrapper(connector.acceptConnection());
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }
}
