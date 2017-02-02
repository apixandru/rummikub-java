package com.apixandru.rummikub.server;

import com.apixandru.rummikub.model.Rummikub;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 09, 2016
 */
interface StateChangeListener {

    void enteredWaitingRoom(RummikubRoomConfigurer configurer);

    void enteredGame(Rummikub<Integer> rummikub);

}
