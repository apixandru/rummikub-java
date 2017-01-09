package com.apixandru.rummikub.server;

import com.apixandru.rummikub.api.config.GameConfigurer;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 09, 2016
 */
public interface StateChangeListener {

    void enteredWaitingRoom(RummikubRoomConfigurer configurer);

    void enteredGame(GameConfigurer configurer);

}
