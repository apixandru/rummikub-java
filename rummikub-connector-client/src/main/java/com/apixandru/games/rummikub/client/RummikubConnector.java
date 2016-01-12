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

    /**
     * @param callback the callback
     */
    private RummikubConnector(final PlayerCallback<E> callback) {
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
     * @param boardCallback the board callback
     * @return this
     */
    public RummikubConnector<E> setBoardCallback(final BoardCallback boardCallback) {
        this.boardCallback = boardCallback;
        return this;
    }

    /**
     * @param gameEventListener the game event listener
     * @return this
     */
    public RummikubConnector<E> setGameEventListener(final GameEventListener gameEventListener) {
        this.gameEventListener = gameEventListener;
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
    public static <H> RummikubConnector<H> from(final PlayerCallback<H> callback) {
        return new RummikubConnector<>(callback);
    }

}
