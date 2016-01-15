package com.apixandru.games.rummikub.server;

import com.apixandru.games.rummikub.api.CompoundCallback;
import com.apixandru.games.rummikub.api.Player;
import com.apixandru.games.rummikub.brotocol.SocketWrapper;
import com.apixandru.games.rummikub.model.Rummikub;
import com.apixandru.games.rummikub.model.RummikubFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 10, 2016
 */
final class RummikubServer {

    private static final Logger log = LoggerFactory.getLogger(RummikubServer.class);

    /**
     * @param serverSocket the server socket
     */
    RummikubServer(final ServerSocket serverSocket) throws IOException {
        log.debug("Listening on port {}", serverSocket.getLocalPort());

        final Rummikub<Integer> game = RummikubFactory.newInstance();

        while (true) {
            log.debug("Waiting for client...");
            final SocketWrapper wrapper = new SocketWrapper(serverSocket.accept());

            final String playerName = wrapper.readString();
            log.debug("Accepted {}.", playerName);

            log.debug("Registering {}...", playerName);
            final CompoundCallback<Integer> callback = new ClientCallback(playerName, wrapper);

            final Player<Integer> player = game.addPlayer(playerName, callback);
            log.debug("{} registered.", playerName);
            final ClientRunnable runnable = new ClientRunnable(wrapper, player, game);
            new Thread(runnable, playerName).start();
        }

    }

    /**
     * @param constant the enum constant
     * @return the ordinal value of the constant or -1 if null
     */
    private static int ordinal(final Enum<?> constant) {
        return null == constant ? -1 : constant.ordinal();
    }

}
