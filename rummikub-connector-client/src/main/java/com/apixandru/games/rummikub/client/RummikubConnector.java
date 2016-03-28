package com.apixandru.games.rummikub.client;

import com.apixandru.games.rummikub.api.BoardCallback;
import com.apixandru.games.rummikub.api.GameEventListener;
import com.apixandru.games.rummikub.api.Player;
import com.apixandru.games.rummikub.api.PlayerCallback;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 08, 2016
 */
public final class RummikubConnector<E> {

    final PlayerCallback<E> callback;
    BoardCallback boardCallback;
    GameEventListener gameEventListener;

    List<E> hints;
    ConnectionListener connectionListener;
    String playerName;

    private RummikubConnector(final PlayerCallback<E> callback) {
        this.callback = callback;
    }

    public static <H> RummikubConnector<H> from(final PlayerCallback<H> callback) {
        return new RummikubConnector<>(callback);
    }

    public RummikubConnector<E> setConnectionListener(final ConnectionListener connectionListener) {
        this.connectionListener = connectionListener;
        return this;
    }

    public RummikubConnector<E> setHints(final List<E> hints) {
        this.hints = hints;
        return this;
    }

    public RummikubConnector<E> setPlayerName(final String name) {
        this.playerName = name;
        return this;
    }

    public RummikubConnector<E> setBoardCallback(final BoardCallback boardCallback) {
        this.boardCallback = boardCallback;
        return this;
    }

    public RummikubConnector<E> setGameEventListener(final GameEventListener gameEventListener) {
        this.gameEventListener = gameEventListener;
        return this;
    }

    public Player<E> link(final Socket socket) throws IOException {
        return RummikubGame.connect(this, socket);
    }

}
