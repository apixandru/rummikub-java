package com.apixandru.games.rummikub.server;

import com.apixandru.games.rummikub.brotocol.SocketWrapper;
import com.apixandru.rummikub.StateChangeListener;
import com.apixandru.rummikub.game.GameConfigurer;
import com.apixandru.rummikub.waiting.ServerWaitingRoomListener;
import com.apixandru.rummikub.waiting.WaitingRoomConfigurator;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 13, 2016
 */
class ServerStateChangeListener implements StateChangeListener<Integer> {

    private final SocketWrapper socketWrapper;

    ServerStateChangeListener(final String playerName, final SocketWrapper socketWrapper) {
        this.socketWrapper = socketWrapper;
    }

    @Override
    public void enteredWaitingRoom(final WaitingRoomConfigurator configurator) {
        configurator.registerListener(new ServerWaitingRoomListener(socketWrapper));
    }

    @Override
    public void enteredGame(final GameConfigurer<Integer> configurer) {

    }

}
