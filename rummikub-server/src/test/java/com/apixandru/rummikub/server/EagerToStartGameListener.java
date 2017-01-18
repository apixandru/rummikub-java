package com.apixandru.rummikub.server;

import com.apixandru.rummikub.model.Rummikub;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 14, 2016
 */
final class EagerToStartGameListener implements StateChangeListener {

    @Override
    public void enteredWaitingRoom(RummikubRoomConfigurer configurer) {
        configurer.startGame();
    }

    @Override
    public void enteredGame(final Rummikub<Integer> rummikub) {
    }

}
