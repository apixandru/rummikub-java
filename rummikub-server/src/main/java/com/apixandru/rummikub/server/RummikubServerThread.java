package com.apixandru.rummikub.server;

import com.apixandru.rummikub.brotocol.SocketWrapper;
import com.apixandru.rummikub.brotocol2.Connection;
import com.apixandru.rummikub.brotocol2.Connector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import static java.util.Optional.empty;
import static java.util.Optional.of;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 04, 2016
 */
class RummikubServerThread extends Thread {

    private static final Logger log = LoggerFactory.getLogger(RummikubServerThread.class);

    private final Connector connector;

    private final AtomicBoolean continueListening = new AtomicBoolean(true);
    private final AtomicBoolean acceptedConnection = new AtomicBoolean(true);

    RummikubServerThread(Connector connector) {
        setName("Server");
        this.connector = connector;
    }

    @Override
    public void run() {
        final ConnectionHandler joiner = new ConnectionHandler();
        log.debug("Listening on port {}", connector.getPort());
        while (continueListening.get()) {
            if (acceptedConnection.get()) {
                log.debug("Waiting for client...");
            }
            newConnection().ifPresent(joiner::attemptToJoin);
        }
    }

    private Optional<SocketWrapper> newConnection() {
        try {
            Connection connection = connector.acceptConnection();
            SocketWrapper socketWrapper = new SocketWrapper(connection);
            acceptedConnection.set(true);
            return of(socketWrapper);
        } catch (SocketTimeoutException timeout) {
            acceptedConnection.set(false);
            return empty();
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage(), e);
        }
    }

    public void stopListening() {
        log.debug("Stop requested");
        continueListening.set(false);
        interrupt();
    }

}
