package com.apixandru.games.rummikub.client;

import com.apixandru.games.rummikub.brotocol.SocketWrapper;

import java.io.IOException;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 04, 2016
 */
final class RummikubGame {

    static <H> SocketPlayer<H> connect(final ConnectorBuilder<H> connector, final SocketWrapper socketWrapper) throws IOException {
        final PlayerCallbackAdapter<H> adapter = new PlayerCallbackAdapter<>(connector, socketWrapper);

        adapter.gameEventListeners.add(connector.gameEventListener);
        adapter.boardCallbacks.add(connector.boardCallback);
        adapter.playerCallbacks.add(connector.callback);
        adapter.connectionListeners.add(connector.connectionListener);

        new Thread(adapter, "Callback adapter").start();

        return new SocketPlayer<>(connector.playerName, socketWrapper, connector.hints);
    }

}
