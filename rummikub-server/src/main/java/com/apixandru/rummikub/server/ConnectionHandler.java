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
class ConnectionHandler {

    private static final Logger log = LoggerFactory.getLogger(ConnectionHandler.class);

    private final RummikubImpl rummikub = new RummikubImpl();

    private static void reject(final SocketWrapper socketWrapper, final Exception exception) {
        socketWrapper.write(false);
        socketWrapper.write(exception.getMessage());
        log.debug("Rejected.", exception);
    }

    synchronized void attemptToJoin(final SocketWrapper wrapper) {
        try {
            final String playerName = wrapper.readString();
            log.debug("{} is attempting to join.", playerName);
            accept(wrapper, playerName);
        } catch (final RummikubException | IOException ex) {
            reject(wrapper, ex);
        }
    }

    private void accept(final SocketWrapper wrapper, final String playerName) {
        ConnectionListener connectionListener = () -> rummikub.unregister(playerName);
        ServerStateChangeListener stateChangeListener = new ServerStateChangeListener(playerName, wrapper, connectionListener);
        rummikub.validateCanJoin(playerName, stateChangeListener);
        wrapper.write(true);
        rummikub.addPlayer(playerName, stateChangeListener);
        log.debug("Accepted.");
        new Thread(stateChangeListener, playerName).start();
    }

}
