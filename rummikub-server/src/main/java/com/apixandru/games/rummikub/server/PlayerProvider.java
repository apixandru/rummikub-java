package com.apixandru.games.rummikub.server;

import com.apixandru.games.rummikub.api.Player;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 06, 2016
 */
public interface PlayerProvider<H> {

    Player<H> getPlayer();

}
