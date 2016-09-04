package com.apixandru.rummikub.api;

import com.apixandru.rummikub.api.room.RummikubRoomConfigurer;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 09, 2016
 */
public interface StateChangeListener {

    void enteredWaitingRoom(RummikubRoomConfigurer configurer);

    void enteredGame(GameConfigurer configurer);

}
