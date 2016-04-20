package com.apixandru.rummikub;

import com.apixandru.rummikub.game.GameConfigurer;
import com.apixandru.rummikub.waiting.WaitingRoomConfigurer;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 09, 2016
 */
public interface StateChangeListener {

    void enteredWaitingRoom(WaitingRoomConfigurer configurer);

    void enteredGame(GameConfigurer configurer);

}
