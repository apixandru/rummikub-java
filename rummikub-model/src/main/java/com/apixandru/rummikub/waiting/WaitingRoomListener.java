package com.apixandru.rummikub.waiting;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 09, 2016
 */
public interface WaitingRoomListener {

    void playerJoined(String playerName);

    void playerLeft(String playerName);

}