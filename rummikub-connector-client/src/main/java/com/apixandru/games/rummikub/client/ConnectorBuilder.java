package com.apixandru.games.rummikub.client;

import com.apixandru.games.rummikub.api.Player;

import java.util.List;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 08, 2016
 */
public final class ConnectorBuilder<E> {

    final List<E> hints;
    String playerName;

    private ConnectorBuilder(final List<E> hints) {
        this.hints = hints;
    }

    public static <H> ConnectorBuilder<H> from(final List<H> hints) {
        return new ConnectorBuilder<>(hints);
    }

    public ConnectorBuilder<E> setPlayerName(final String name) {
        this.playerName = name;
        return this;
    }

    public Player<E> link(final PlayerCallbackAdapter adapter) {
        return RummikubGame.connect(this, adapter);
    }

}
