package com.apixandru.rummikub.api.config;

import com.apixandru.rummikub.api.room.RummikubRoomListener;
import com.apixandru.rummikub.api.room.StartGameListener;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 09, 2016
 */
public interface RummikubRoomConfigurer extends StartGameListener {

    void registerListener(RummikubRoomListener listener);

    void unregisterListener(RummikubRoomListener listener);

}