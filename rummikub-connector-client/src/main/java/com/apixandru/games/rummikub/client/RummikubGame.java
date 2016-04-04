package com.apixandru.games.rummikub.client;

import java.io.IOException;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 04, 2016
 */
final class RummikubGame {

    static <H> SocketPlayer<H> connect(final ConnectorBuilder<H> connector, final PlayerCallbackAdapter<H> adapter) throws IOException {
        new Thread(adapter, "Callback adapter").start();
        return new SocketPlayer<>(connector.playerName, adapter.socketWrapper, connector.hints);
    }

}
