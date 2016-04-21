package com.apixandru.games.rummikub.server;

import com.apixandru.Joiner;
import com.apixandru.games.rummikub.api.Player;
import com.apixandru.games.rummikub.brotocol.SocketWrapper;
import com.apixandru.games.rummikub.model.Rummikub;
import com.apixandru.games.rummikub.model.RummikubFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.apixandru.games.rummikub.server.ServerState.IN_GAME;
import static com.apixandru.games.rummikub.server.ServerState.WAITING_ROOM;

/**
 * @author Alexandru-Constantin Bledea
 * @since March 31, 2016
 */
public class OldConnectionHandler implements Joiner {

    private static final Logger log = LoggerFactory.getLogger(RummikubServer.class);

    private final Rummikub<Integer> game = RummikubFactory.newInstance();
    private final Map<String, SocketWrapper> activeConnections = new HashMap<>();
    private ServerState serverState = WAITING_ROOM;

    private static void reject(final SocketWrapper socketWrapper, String message) {
        socketWrapper.write(false);
        socketWrapper.write(message);
        log.debug("Rejected.");
    }

    @Override
    public synchronized void attemptToJoin(final SocketWrapper wrapper) throws IOException {
        final String playerName = wrapper.readString();
        log.debug("{} is attempting to join.", playerName);
        if (usernameTaken(playerName)) {
            reject(wrapper, "Username is already taken.");
            return;
        }
        if (onGoingGame()) {
            reject(wrapper, "You cannot join because there is an ongoing game.");
            return;
        }
        accept(wrapper, playerName);
        log.debug("Registering {}...", playerName);
        final ClientCallback callback = new ClientCallback(playerName, wrapper);

        game.addBoardListener(callback);
        game.addGameEventListener(callback);
        final Player<Integer> player = game.addPlayer(playerName, callback);
        log.debug("{} registered.", playerName);
        final ClientRunnable runnable = new ClientRunnable(wrapper, () -> player, game, callback);
        new Thread(runnable, playerName).start();
    }

    private boolean onGoingGame() {
        return IN_GAME == serverState;
    }

    private boolean usernameTaken(final String playerName) {
        return activeConnections.containsKey(playerName);
    }

    private void accept(final SocketWrapper wrapper, final String playerName) {
        wrapper.write(true);
        activeConnections.put(playerName, wrapper);
        log.debug("Accepted.");
    }

}
