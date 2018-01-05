package com.apixandru.rummikub.client;

import com.apixandru.rummikub.brotocol.connect.server.PacketPlayerJoined;
import com.apixandru.rummikub.brotocol.connect.server.PacketPlayerLeft;
import com.apixandru.rummikub.brotocol.connect.server.PacketPlayerStart;
import com.apixandru.rummikub.brotocol.room.RummikubRoomListener;
import com.apixandru.rummikub.brotocol.room.StartGameListener;
import com.apixandru.rummikub.brotocol.util.AbstractMultiPacketHandler;
import com.apixandru.rummikub.client.waiting.PlayerJoinedHandler;
import com.apixandru.rummikub.client.waiting.PlayerLeftHandler;
import com.apixandru.rummikub.client.waiting.PlayerStartHandler;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 19, 2017
 */
public class ClientWaitingRoomPacketHandler extends AbstractMultiPacketHandler {

    ClientWaitingRoomPacketHandler(StartGameListener startGameListener) {
        register(PacketPlayerStart.class, new PlayerStartHandler(startGameListener));
    }

    public void setWaitingRoomListener(final RummikubRoomListener waitingRoomListener) {
        register(PacketPlayerJoined.class, new PlayerJoinedHandler(waitingRoomListener));
        register(PacketPlayerLeft.class, new PlayerLeftHandler(waitingRoomListener));
    }

}
