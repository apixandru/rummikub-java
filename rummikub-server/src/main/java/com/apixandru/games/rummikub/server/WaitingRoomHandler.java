package com.apixandru.games.rummikub.server;

import com.apixandru.games.rummikub.brotocol.PacketHandler;
import com.apixandru.games.rummikub.brotocol.connect.client.PacketJoin;

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

    private class JoinHandler implements PacketHandler<PacketJoin> {

        @Override
        public void handle(final PacketJoin packet) {
            playerJoined(packet.playerName);
        }

    }

}
