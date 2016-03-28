package com.apixandru.games.rummikub.client;

import com.apixandru.games.rummikub.brotocol.SocketWrapper;

import java.io.IOException;
import java.net.Socket;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 04, 2016
 */
final class RummikubGame {

    static <H> SocketPlayer<H> connect(final RummikubConnector<H> connector, final Socket socket) throws IOException {
        final SocketWrapper wrapper = new SocketWrapper(socket);
        sendPlayerName(connector.playerName, wrapper);
        new Thread(new PlayerCallbackAdapter<>(connector, wrapper), "Callback adapter").start();

        return new SocketPlayer<>(connector.playerName, wrapper, connector.hints);
    }

    private static void sendPlayerName(final String playerName, final SocketWrapper wrapper) {
        wrapper.write(playerName);
    }

}
