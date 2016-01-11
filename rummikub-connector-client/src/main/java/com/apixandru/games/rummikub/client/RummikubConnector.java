package com.apixandru.games.rummikub.client;

import com.apixandru.games.rummikub.api.CompoundCallback;
import com.apixandru.games.rummikub.api.Player;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 08, 2016
 */
public final class RummikubConnector<E> {

    final CompoundCallback<E> callback;

    List<E> hints;
    ConnectionListener connectionListener;
    String playerName;

    /**
     * @param callback the callback
     */
    private RummikubConnector(final CompoundCallback<E> callback) {
        this.callback = callback;
    }

    /**
     * @param connectionListener the connection listener
     * @return this
     */
    public RummikubConnector<E> setConnectionListener(final ConnectionListener connectionListener) {
        this.connectionListener = connectionListener;
        return this;
    }

    /**
     * @param hints the client hints
     * @return this
     */
    public RummikubConnector<E> setHints(final List<E> hints) {
        this.hints = hints;
        return this;
    }

    /**
     * @param name the player name
     * @return this
     */
    public RummikubConnector<E> setPlayerName(final String name) {
        this.playerName = name;
        return this;
    }

    /**
     * @param socket the socket
     * @return this
     * @throws IOException
     */
    public Player<E> link(final Socket socket) throws IOException {
        return RummikubGame.connect(this, socket);
    }

    /**
     * @param callback the player callback
     * @return a new rummikub connector
     */
    public static <H> RummikubConnector<H> from(final CompoundCallback<H> callback) {
        return new RummikubConnector<>(callback);
    }

}
