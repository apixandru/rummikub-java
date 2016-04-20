package com.apixandru.rummikub.server;

import com.apixandru.games.rummikub.brotocol.SocketWrapper;
import com.apixandru.rummikub2.Rummikub;
import com.apixandru.rummikub2.RummikubException;
import com.apixandru.rummikub2.RummikubImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 13, 2016
 */
public class ConnectionHandler {

    private static final Logger log = LoggerFactory.getLogger(ConnectionHandler.class);

    private final Rummikub rummikub = new RummikubImpl();

    private static void reject(final SocketWrapper socketWrapper, final RummikubException exception) {
        socketWrapper.write(false);
        socketWrapper.write(exception.getMessage());
        log.debug("Rejected.", exception);
    }

    public synchronized void attemptToJoin(final SocketWrapper wrapper) throws IOException {
        final String playerName = wrapper.readString();
        log.debug("{} is attempting to join.", playerName);
        try {
            rummikub.register(playerName, new ServerStateChangeListener(playerName, wrapper));
        } catch (final RummikubException ex) {
            reject(wrapper, ex);
        }
        log.debug("{} registered.", playerName);
    }

}
