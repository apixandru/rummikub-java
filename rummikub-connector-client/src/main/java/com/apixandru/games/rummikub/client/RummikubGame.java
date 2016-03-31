package com.apixandru.games.rummikub.client;

import com.apixandru.games.rummikub.brotocol.SocketWrapper;

import java.io.IOException;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 04, 2016
 */
final class RummikubGame {

    static <H> SocketPlayer<H> connect(final RummikubConnector<H> connector, final SocketWrapper socketWrapper) throws IOException {
        sendPlayerName(connector.playerName, socketWrapper);
        new Thread(new PlayerCallbackAdapter<>(connector, socketWrapper), "Callback adapter").start();

        return new SocketPlayer<>(connector.playerName, socketWrapper, connector.hints);
    }

    private static void sendPlayerName(final String playerName, final SocketWrapper wrapper) {
        wrapper.write(playerName);
    }

}
