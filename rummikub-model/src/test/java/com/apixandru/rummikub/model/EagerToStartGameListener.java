package com.apixandru.rummikub.model;

import com.apixandru.rummikub.api.GameConfigurer;
import com.apixandru.rummikub.api.StateChangeListener;
import com.apixandru.rummikub.api.WaitingRoomConfigurer;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 14, 2016
 */
class EagerToStartGameListener implements StateChangeListener {

    @Override
    public void enteredWaitingRoom(final WaitingRoomConfigurer configurer) {
        configurer.startGame();
    }

    @Override
    public void enteredGame(final GameConfigurer configurer) {

    }

}
