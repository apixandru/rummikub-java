package com.apixandru.rummikub.api;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 09, 2016
 */
public interface WaitingRoomConfigurer extends StartGameListener {

    void registerListener(WaitingRoomListener listener);

    void unregisterListener(WaitingRoomListener listener);

}
