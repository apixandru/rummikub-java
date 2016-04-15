package com.apixandru.rummikub2;

import com.apixandru.rummikub.StateChangeListener;
import com.apixandru.rummikub.game.GameConfigurer;
import com.apixandru.rummikub.waiting.WaitingRoomConfigurator;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 14, 2016
 */
public class EagerToStartGameListener<H> implements StateChangeListener<H> {

    @Override
    public void enteredWaitingRoom(final WaitingRoomConfigurator configurator) {
        configurator.getStartGameListener().startGame();
    }

    @Override
    public void enteredGame(final GameConfigurer<H> configurer) {

    }

}
