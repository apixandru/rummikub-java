package com.apixandru.rummikub.server;

import com.apixandru.rummikub.api.room.StartGameListener;
import com.apixandru.rummikub.brotocol.SocketWrapper;
import com.apixandru.rummikub.brotocol.connect.client.StartGameRequest;
import com.apixandru.rummikub.brotocol.util.MultiPacketHandler;
import com.apixandru.rummikub.server.room.Room;
import com.apixandru.rummikub.server.waiting.ServerRummikubRoomListener;
import com.apixandru.rummikub.server.waiting.StartGameHandler;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 15, 2017
 */
class WaitingRoomPacketHandler extends MultiPacketHandler implements TidyPacketHandler {

    private final String playerName;
    private final Room room;

    WaitingRoomPacketHandler(String playerName, SocketWrapper socketWrapper, Room room, StartGameListener startGameListener) {
        this.room = room;
        this.playerName = playerName;

        room.join(playerName, new ServerRummikubRoomListener(socketWrapper));

        register(StartGameRequest.class, new StartGameHandler(startGameListener));
    }

    @Override
    public void cleanup() {
        this.room.leave(this.playerName);
    }

}
