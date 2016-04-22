package com.apixandru.rummikub.model;

import com.apixandru.rummikub.model.game.GameConfigurer;
import com.apixandru.rummikub.model.waiting.WaitingRoomConfigurer;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 09, 2016
 */
public interface StateChangeListener {

    void enteredWaitingRoom(WaitingRoomConfigurer configurer);

    void enteredGame(GameConfigurer configurer);

}
