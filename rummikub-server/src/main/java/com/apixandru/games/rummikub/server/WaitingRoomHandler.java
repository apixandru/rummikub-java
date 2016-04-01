package com.apixandru.games.rummikub.server;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Alexandru-Constantin Bledea
 * @since March 30, 2016
 */
public class WaitingRoomHandler {

    private Map<String, Boolean> players = new HashMap<>();

    private void playerJoined(final String playerName) {
        this.players.put(playerName, false);
    }

}
