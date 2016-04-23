package com.apixandru.rummikub.api;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 09, 2016
 */
public interface StateChangeListener {

    void enteredWaitingRoom(WaitingRoomConfigurer configurer);

    void enteredGame(GameConfigurer configurer);

}
