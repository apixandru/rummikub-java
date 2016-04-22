package com.apixandru.rummikub.server;

import com.apixandru.rummikub.brotocol.SocketWrapper;
import com.apixandru.rummikub.brotocol.util.ConnectionListener;
import com.apixandru.rummikub.model.RummikubException;
import com.apixandru.rummikub.model.RummikubImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 13, 2016
 */
public class ConnectionHandler {

    private static final Logger log = LoggerFactory.getLogger(ConnectionHandler.class);

    private final RummikubImpl rummikub = new RummikubImpl();

    private static void reject(final SocketWrapper socketWrapper, final RummikubException exception) {
        socketWrapper.write(false);
        socketWrapper.write(exception.getMessage());
        log.debug("Rejected.", exception);
    }

    public synchronized void attemptToJoin(final SocketWrapper wrapper) throws IOException {
        final String playerName = wrapper.readString();
        log.debug("{} is attempting to join.", playerName);
        try {
            accept(wrapper, playerName);
        } catch (final RummikubException ex) {
            reject(wrapper, ex);
        }
    }

    private void accept(final SocketWrapper wrapper, final String playerName) {
        ConnectionListener connectionListener = () -> rummikub.unregister(playerName);
        ServerStateChangeListener stateChangeListener = new ServerStateChangeListener(playerName, wrapper, connectionListener);
        rummikub.register(playerName, stateChangeListener);
        wrapper.write(true);
        log.debug("Accepted.");
        new Thread(stateChangeListener).start();
    }
}
