package com.apixandru.rummikub.server.waiting;

import com.apixandru.rummikub.brotocol.PacketWriter;
import com.apixandru.rummikub.brotocol.connect.client.StartGameRequest;
import com.apixandru.rummikub.brotocol.room.StartGameListener;
import com.apixandru.rummikub.brotocol.util.MultiPacketHandler;
import com.apixandru.rummikub.server.TidyPacketHandler;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 15, 2017
 */
public class WaitingRoomPacketHandler extends MultiPacketHandler implements TidyPacketHandler {

    private final String playerName;
    private final Room room;

    public WaitingRoomPacketHandler(String playerName, PacketWriter packetWriter, Room room, StartGameListener startGameListener) {
        this.room = room;
        this.playerName = playerName;

        room.join(playerName, new ServerRummikubRoomListener(packetWriter));

        register(StartGameRequest.class, new StartGameHandler(startGameListener));
    }

    @Override
    public void cleanup() {
        this.room.leave(this.playerName);
    }

}
