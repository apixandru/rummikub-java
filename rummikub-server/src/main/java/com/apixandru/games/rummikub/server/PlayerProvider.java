package com.apixandru.games.rummikub.server;

import com.apixandru.games.rummikub.api.Player;

import java.util.function.Supplier;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 06, 2016
 */
public interface PlayerProvider<H> extends Supplier<Player<H>> {

    @Override
    Player<H> get();

}
