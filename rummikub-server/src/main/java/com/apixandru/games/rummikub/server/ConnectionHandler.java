package com.apixandru.games.rummikub.server;

import com.apixandru.games.rummikub.api.CompoundCallback;
import com.apixandru.games.rummikub.api.Player;
import com.apixandru.games.rummikub.brotocol.SocketWrapper;
import com.apixandru.games.rummikub.brotocol.connect.server.PacketPlayerJoined;
import com.apixandru.games.rummikub.model.Rummikub;
import com.apixandru.games.rummikub.model.RummikubFactory;
import com.apixandru.games.rummikub.server.game.GameHandler;
import com.apixandru.games.rummikub.server.waiting.WaitingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

import static com.apixandru.games.rummikub.server.ServerState.IN_GAME;
import static com.apixandru.games.rummikub.server.ServerState.WAITING_ROOM;

/**
 * @author Alexandru-Constantin Bledea
 * @since March 31, 2016
 */
public class ConnectionHandler {

    private static final Logger log = LoggerFactory.getLogger(RummikubServer.class);

    private final Rummikub<Integer> game = RummikubFactory.newInstance();
    private final Map<String, SocketWrapper> activeConnections = new HashMap<>();
    private final GameHandler gameHandler = new GameHandler();
    private final WaitingHandler waitingHandler = new WaitingHandler();
    private ServerState serverState = WAITING_ROOM;

    private static Predicate<Map.Entry<String, SocketWrapper>> notCurrentPlayer(final String playerName) {
        return entry -> !entry.getKey().equals(playerName);
    }

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
//        broadcastAcceptedPlayer(playerName);
        log.debug("Registering {}...", playerName);
        final CompoundCallback<Integer> callback = new ClientCallback(playerName, wrapper);

        final Player<Integer> player = game.addPlayer(playerName, callback);
        log.debug("{} registered.", playerName);
        final ClientRunnable runnable = new ClientRunnable(wrapper, player, game);
        new Thread(runnable, playerName).start();
    }

    private void broadcastAcceptedPlayer(final String playerName) {

        final PacketPlayerJoined packetJoined = new PacketPlayerJoined();
        packetJoined.playerName = playerName;

        activeConnections.entrySet()
                .stream()
                .filter(notCurrentPlayer(playerName))
                .map(Map.Entry::getValue)
                .forEach(socketWrapper -> socketWrapper.writePacket(packetJoined));
    }

    private boolean onGoingGame() {
        return IN_GAME == serverState;
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
