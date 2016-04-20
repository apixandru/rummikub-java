package com.apixandru.rummikub2;

import com.apixandru.rummikub.StateChangeListener;
import com.apixandru.rummikub.game.GameConfigurer;
import com.apixandru.rummikub.waiting.WaitingRoomConfigurer;

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
