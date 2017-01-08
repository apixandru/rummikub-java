package com.apixandru.rummikub.server;

import com.apixandru.rummikub.api.config.GameConfigurer;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 14, 2016
 */
final class EagerToStartGameListener implements StateChangeListener {

    @Override
    public void enteredWaitingRoom(RummikubImpl configurer) {
        configurer.startGame();
    }

    @Override
    public void enteredGame(final GameConfigurer configurer) {
    }

}
