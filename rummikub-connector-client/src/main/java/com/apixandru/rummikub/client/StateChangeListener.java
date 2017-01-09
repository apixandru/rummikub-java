package com.apixandru.rummikub.client;

import com.apixandru.rummikub.api.config.GameConfigurer;
import com.apixandru.rummikub.client.waiting.RummikubRoomConfigurer;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 09, 2016
 */
public interface StateChangeListener {

    void enteredWaitingRoom(RummikubRoomConfigurer configurer);

    void enteredGame(GameConfigurer configurer);

}
