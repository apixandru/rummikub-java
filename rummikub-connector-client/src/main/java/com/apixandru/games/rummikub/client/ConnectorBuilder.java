package com.apixandru.games.rummikub.client;

import com.apixandru.games.rummikub.api.BoardCallback;
import com.apixandru.games.rummikub.api.GameEventListener;
import com.apixandru.games.rummikub.api.Player;
import com.apixandru.games.rummikub.api.PlayerCallback;
import com.apixandru.games.rummikub.brotocol.SocketWrapper;

import java.io.IOException;
import java.util.List;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 08, 2016
 */
public final class ConnectorBuilder<E> {

    final PlayerCallback<E> callback;
    BoardCallback boardCallback;
    GameEventListener gameEventListener;

    List<E> hints;
    ConnectionListener connectionListener;
    String playerName;

    private ConnectorBuilder(final PlayerCallback<E> callback) {
        this.callback = callback;
    }

    public static <H> ConnectorBuilder<H> from(final PlayerCallback<H> callback) {
        return new ConnectorBuilder<>(callback);
    }

    public ConnectorBuilder<E> setConnectionListener(final ConnectionListener connectionListener) {
        this.connectionListener = connectionListener;
        return this;
    }

    public ConnectorBuilder<E> setHints(final List<E> hints) {
        this.hints = hints;
        return this;
    }

    public ConnectorBuilder<E> setPlayerName(final String name) {
        this.playerName = name;
        return this;
    }

    public ConnectorBuilder<E> setBoardCallback(final BoardCallback boardCallback) {
        this.boardCallback = boardCallback;
        return this;
    }

    public ConnectorBuilder<E> setGameEventListener(final GameEventListener gameEventListener) {
        this.gameEventListener = gameEventListener;
        return this;
    }

    public Player<E> link(final PlayerCallbackAdapter<E> adapter) throws IOException {
        return RummikubGame.connect(this, adapter);
    }

}
