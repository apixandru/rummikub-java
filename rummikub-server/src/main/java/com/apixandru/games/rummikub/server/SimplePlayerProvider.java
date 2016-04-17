package com.apixandru.games.rummikub.server;

import com.apixandru.games.rummikub.api.Player;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 06, 2016
 */
public class SimplePlayerProvider implements PlayerProvider<Integer> {

    private final Player<Integer> player;

    public SimplePlayerProvider(final Player<Integer> player) {
        this.player = player;
    }

    @Override
    public Player<Integer> get() {
        return player;
    }
}
