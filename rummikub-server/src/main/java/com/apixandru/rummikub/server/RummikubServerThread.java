package com.apixandru.rummikub.server;

import com.apixandru.rummikub.brotocol.SocketWrapper;
import com.apixandru.rummikub.brotocol2.Connector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 04, 2016
 */
class RummikubServerThread extends Thread {

    private static final Logger log = LoggerFactory.getLogger(RummikubServerThread.class);

    private final Connector connector;

    private final AtomicBoolean continueListening = new AtomicBoolean(true);

    RummikubServerThread(Connector connector) {
        setName("Server");
        this.connector = connector;
    }

    @Override
    public void run() {
        final ConnectionHandler joiner = new ConnectionHandler();
        log.debug("Listening on port {}", connector.getPort());
        while (continueListening.get()) {
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

    public void stopListening() {
        continueListening.set(false);
        interrupt();
    }

}
