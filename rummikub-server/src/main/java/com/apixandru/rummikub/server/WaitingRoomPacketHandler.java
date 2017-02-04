package com.apixandru.rummikub.server;

import com.apixandru.rummikub.brotocol.SocketWrapper;
import com.apixandru.rummikub.brotocol.connect.client.StartGameRequest;
import com.apixandru.rummikub.brotocol.util.MultiPacketHandler;
import com.apixandru.rummikub.server.waiting.ServerRummikubRoomListener;
import com.apixandru.rummikub.server.waiting.StartGameHandler;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 15, 2017
 */
class WaitingRoomPacketHandler extends MultiPacketHandler implements TidyPacketHandler {

    private final RummikubRoomConfigurer roomConfigurer;
    private final String playerName;

    WaitingRoomPacketHandler(String playerName, SocketWrapper socketWrapper, RummikubRoomConfigurer roomConfigurer) {
        this.roomConfigurer = roomConfigurer;
        this.playerName = playerName;
        this.roomConfigurer.registerListener(playerName, new ServerRummikubRoomListener(socketWrapper));

        register(StartGameRequest.class, new StartGameHandler(roomConfigurer));
    }

    @Override
    public void cleanup() {
        this.roomConfigurer.unregisterListener(this.playerName);
    }

}
