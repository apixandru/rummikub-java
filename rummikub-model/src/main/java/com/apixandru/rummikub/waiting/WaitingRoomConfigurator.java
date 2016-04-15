package com.apixandru.rummikub.waiting;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 09, 2016
 */
public interface WaitingRoomConfigurator {

    void registerListener(WaitingRoomListener listener);

    StartGameListener getStartGameListener();

}
