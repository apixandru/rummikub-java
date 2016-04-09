package com.apixandru.games.rummikub.brotocol.connect;

import com.apixandru.rummikub.waiting.StartGameListener;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 01, 2016
 */
public interface WaitingRoomListener extends StartGameListener {

    void playerJoined(String playerName);

    void playerLeft(String playerName);

}
