package com.apixandru.rummikub.client;

import com.apixandru.rummikub.api.room.RummikubRoomListener;
import com.apixandru.rummikub.api.room.StartGameListener;
import com.apixandru.rummikub.brotocol.ConnectorPacketHandler;
import com.apixandru.rummikub.brotocol.connect.server.PacketPlayerJoined;
import com.apixandru.rummikub.brotocol.connect.server.PacketPlayerLeft;
import com.apixandru.rummikub.brotocol.connect.server.PacketPlayerStart;
import com.apixandru.rummikub.brotocol.util.MultiPacketHandler;
import com.apixandru.rummikub.client.waiting.PlayerJoinedHandler;
import com.apixandru.rummikub.client.waiting.PlayerLeftHandler;
import com.apixandru.rummikub.client.waiting.PlayerStartHandler;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 19, 2017
 */
public class ClientWaitingRoomPacketHandler extends MultiPacketHandler implements ConnectorPacketHandler {

    ClientWaitingRoomPacketHandler(StartGameListener startGameListener) {
        register(PacketPlayerStart.class, new PlayerStartHandler(startGameListener));
    }

    public void setWaitingRoomListener(final RummikubRoomListener waitingRoomListener) {
        register(PacketPlayerJoined.class, new PlayerJoinedHandler(waitingRoomListener));
        register(PacketPlayerLeft.class, new PlayerLeftHandler(waitingRoomListener));
    }

    @Override
    public boolean isReady() {
        return true;
    }

}
