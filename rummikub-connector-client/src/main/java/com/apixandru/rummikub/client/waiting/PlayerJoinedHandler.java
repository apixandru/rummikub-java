package com.apixandru.rummikub.client.waiting;

import com.apixandru.rummikub.api.room.RummikubRoomListener;
import com.apixandru.rummikub.brotocol.PacketHandler;
import com.apixandru.rummikub.brotocol.connect.server.PacketPlayerJoined;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 01, 2016
 */
public final class PlayerJoinedHandler implements PacketHandler<PacketPlayerJoined> {

    private static final Logger log = LoggerFactory.getLogger(PlayerJoinedHandler.class);

    private final RummikubRoomListener waitingRoomListeners;

    public PlayerJoinedHandler(final RummikubRoomListener waitingRoomListeners) {
        this.waitingRoomListeners = waitingRoomListeners;
    }

    @Override
    public void handle(final PacketPlayerJoined packet) {
        log.info("Player {} joined.", packet.playerName);
        waitingRoomListeners.playerJoined(packet.playerName);
    }

}
