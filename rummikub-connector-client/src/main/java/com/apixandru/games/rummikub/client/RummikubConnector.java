package com.apixandru.games.rummikub.client;

import com.apixandru.games.rummikub.brotocol.SocketWrapper;
import com.apixandru.rummikub.StateChangeListener;
import com.apixandru.rummikub.game.GameConfigurerAdapter;
import com.apixandru.rummikub.waiting.WaitingRoomConfigurer;
import com.apixandru.rummikub.waiting.WaitingRoomListener;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 02, 2016
 */
public final class RummikubConnector<H> {

    final SocketWrapper socketWrapper;
    final StateChangeListener stateChangeListener;

    public RummikubConnector(final SocketWrapper socketWrapper, final StateChangeListener stateChangeListener) {
        this.socketWrapper = socketWrapper;
        this.stateChangeListener = stateChangeListener;
    }

    public void connect() {
        stateChangeListener.enteredWaitingRoom(new WaitingRoomConfigurer() {
            @Override
            public void registerListener(final WaitingRoomListener listener) {

            }

            @Override
            public void startGame() {
                stateChangeListener.enteredGame(new GameConfigurerAdapter());
            }

        });
    }

}
