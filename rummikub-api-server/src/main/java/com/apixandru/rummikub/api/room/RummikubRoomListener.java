package com.apixandru.rummikub.api.room;

/**
 * @author Alexandru-Constantin Bledea
 * @since Sep 02, 2016
 */
public interface RummikubRoomListener {

    void playerJoined(String playerName);

    void playerLeft(String playerName);

}
