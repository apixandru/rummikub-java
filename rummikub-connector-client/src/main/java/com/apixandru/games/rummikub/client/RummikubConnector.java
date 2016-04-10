package com.apixandru.games.rummikub.client;

import com.apixandru.games.rummikub.brotocol.SocketWrapper;
import com.apixandru.rummikub.StateChangeListener;
import com.apixandru.rummikub.waiting.StartGameListener;
import com.apixandru.rummikub.waiting.WaitingRoomConfigurator;
import com.apixandru.rummikub.waiting.WaitingRoomListener;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 02, 2016
 */
public final class RummikubConnector<H> {

    final SocketWrapper socketWrapper;
    final StateChangeListener<H> stateChangeListener;

    public RummikubConnector(final SocketWrapper socketWrapper, final StateChangeListener<H> stateChangeListener) {
        this.socketWrapper = socketWrapper;
        this.stateChangeListener = stateChangeListener;
    }

    public void connect() {
        stateChangeListener.enteredWaitingRoom(new WaitingRoomConfigurator() {
            @Override
            public void registerListener(final WaitingRoomListener listener) {

            }

            @Override
            public StartGameListener newStartGameListener() {
                return () -> stateChangeListener.enteredGame(null);
            }
        });
    }

}
