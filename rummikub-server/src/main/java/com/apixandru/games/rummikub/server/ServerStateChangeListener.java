package com.apixandru.games.rummikub.server;

import com.apixandru.games.rummikub.brotocol.SocketWrapper;
import com.apixandru.rummikub.StateChangeListener;
import com.apixandru.rummikub.game.GameConfigurer;
import com.apixandru.rummikub.waiting.WaitingRoomConfigurator;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 13, 2016
 */
public class ServerStateChangeListener implements StateChangeListener<Integer> {

    public ServerStateChangeListener(final String playerName, final SocketWrapper wrapper) {

    }

    @Override
    public void enteredWaitingRoom(final WaitingRoomConfigurator configurator) {

    }

    @Override
    public void enteredGame(final GameConfigurer<Integer> configurer) {

    }

}
