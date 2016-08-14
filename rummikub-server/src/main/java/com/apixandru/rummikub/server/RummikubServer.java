package com.apixandru.rummikub.server;

import com.apixandru.rummikub.brotocol.SocketWrapper;
import com.apixandru.rummikub.brotocol2.Connector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

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
