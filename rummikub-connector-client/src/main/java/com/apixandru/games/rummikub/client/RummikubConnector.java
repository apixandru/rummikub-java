package com.apixandru.games.rummikub.client;

import com.apixandru.games.rummikub.api.Player;
import com.apixandru.games.rummikub.api.PlayerCallback;

import java.io.IOException;
import java.util.List;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 08, 2016
 */
public final class RummikubConnector<E> {

    final PlayerCallback<E> callback;

    List<E> hints;
    ConnectionListener connectionListener;
    String playerName;

    /**
     * @param callback
     */
    private RummikubConnector(final PlayerCallback<E> callback) {
        this.callback = callback;
    }

    /**
     * @param connectionListener
     * @return
     */
    public RummikubConnector<E> setConnectionListener(final ConnectionListener connectionListener) {
        this.connectionListener = connectionListener;
        return this;
    }

    /**
     * @param hints
     * @return
     */
    public RummikubConnector<E> setHints(final List<E> hints) {
        this.hints = hints;
        return this;
    }

    /**
     * @param name
     * @return
     */
    public RummikubConnector<E> setPlayerName(final String name) {
        this.playerName = name;
        return this;
    }

    /**
     * @param ipAddress
     * @return
     * @throws IOException
     */
    public Player<E> connectTo(final String ipAddress) throws IOException {
        return RummikubGame.connect(this, ipAddress);
    }

    /**
     * @param callback
     * @return
     */
    public static <H> RummikubConnector<H> from(final PlayerCallback<H> callback) {
        return new RummikubConnector<>(callback);
    }

}
