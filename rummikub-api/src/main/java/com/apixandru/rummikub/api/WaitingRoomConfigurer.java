package com.apixandru.rummikub.api;

import com.apixandru.rummikub.api.room.RummikubRoomListener;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 09, 2016
 */
public interface WaitingRoomConfigurer extends StartGameListener {

    void registerListener(RummikubRoomListener listener);

    void unregisterListener(RummikubRoomListener listener);

}
