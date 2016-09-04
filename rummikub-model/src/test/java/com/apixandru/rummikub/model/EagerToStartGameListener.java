package com.apixandru.rummikub.model;

import com.apixandru.rummikub.api.config.GameConfigurer;
import com.apixandru.rummikub.api.config.RummikubRoomConfigurer;
import com.apixandru.rummikub.api.config.StateChangeListener;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 14, 2016
 */
class EagerToStartGameListener implements StateChangeListener {

    @Override
    public void enteredWaitingRoom(final RummikubRoomConfigurer configurer) {
        configurer.startGame();
    }

    @Override
    public void enteredGame(final GameConfigurer configurer) {

    }

}
