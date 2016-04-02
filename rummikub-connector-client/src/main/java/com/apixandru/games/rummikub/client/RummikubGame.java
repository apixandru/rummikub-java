package com.apixandru.games.rummikub.client;

import com.apixandru.games.rummikub.brotocol.SocketWrapper;

import java.io.IOException;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 04, 2016
 */
final class RummikubGame {

    static <H> SocketPlayer<H> connect(final ConnectorBuilder<H> connector, final SocketWrapper socketWrapper) throws IOException {
        new Thread(new PlayerCallbackAdapter<>(connector, socketWrapper), "Callback adapter").start();

        return new SocketPlayer<>(connector.playerName, socketWrapper, connector.hints);
    }

}
