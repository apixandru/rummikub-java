package com.apixandru.games.rummikub.server;

import com.apixandru.games.rummikub.api.CompoundCallback;
import com.apixandru.games.rummikub.api.Player;
import com.apixandru.games.rummikub.brotocol.SocketWrapper;
import com.apixandru.games.rummikub.model.Rummikub;
import com.apixandru.games.rummikub.model.RummikubFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Alexandru-Constantin Bledea
 * @since March 31, 2016
 */
public class ConnectionHandler {

    private static final Logger log = LoggerFactory.getLogger(RummikubServer.class);

    private final Rummikub<Integer> game = RummikubFactory.newInstance();

    private final Map<String, SocketWrapper> activeConnections = new HashMap<>();

    public synchronized void attemptToJoin(final SocketWrapper wrapper) throws IOException {
        final String playerName = wrapper.readString();
        log.debug("{} is attempting to join.", playerName);
        if (usernameTaken(playerName)) {
            reject(wrapper, "Username is already taken.");
            return;
        }
        accept(wrapper, playerName);

        log.debug("Registering {}...", playerName);
        final CompoundCallback<Integer> callback = new ClientCallback(playerName, wrapper);

        final Player<Integer> player = game.addPlayer(playerName, callback);
        log.debug("{} registered.", playerName);
        final ClientRunnable runnable = new ClientRunnable(wrapper, player, game);
        new Thread(runnable, playerName).start();
    }

    private boolean usernameTaken(final String playerName) {
        return activeConnections.containsKey(playerName);
    }

    private void reject(final SocketWrapper socketWrapper, String message) {
        socketWrapper.write(false);
        socketWrapper.write(message);
        log.debug("Rejected.");
    }

    private void accept(final SocketWrapper wrapper, final String playerName) {
        wrapper.write(true);
        activeConnections.put(playerName, wrapper);
        log.debug("Accepted.");
    }


}
