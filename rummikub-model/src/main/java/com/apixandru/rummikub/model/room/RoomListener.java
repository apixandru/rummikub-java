package com.apixandru.rummikub.model.room;

/**
 * @author Alexandru-Constantin Bledea
 * @since Sep 02, 2016
 */
public interface RoomListener {

    void playerJoined(String playerName);

    void playerLeft(String playerName);

}
