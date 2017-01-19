package com.apixandru.rummikub.server;

import com.apixandru.rummikub.api.room.StartGameListener;
import com.apixandru.rummikub.brotocol.connect.client.StartGameRequest;
import com.apixandru.rummikub.brotocol.util.MultiPacketHandler;
import com.apixandru.rummikub.server.waiting.StartGameHandler;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 15, 2017
 */
public class WaitingRoomPacketHandler extends MultiPacketHandler {

    public WaitingRoomPacketHandler(StartGameListener startGameListener) {
        register(StartGameRequest.class, new StartGameHandler(startGameListener));
    }

}
