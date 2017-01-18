package com.apixandru.rummikub.server;

import com.apixandru.rummikub.api.room.RummikubRoomListener;
import com.apixandru.rummikub.api.room.StartGameListener;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 09, 2016
 */
public interface RummikubRoomConfigurer extends StartGameListener {

    void registerListener(String playerName, final RummikubRoomListener listener);

    void unregisterListener(String playerName);

}
