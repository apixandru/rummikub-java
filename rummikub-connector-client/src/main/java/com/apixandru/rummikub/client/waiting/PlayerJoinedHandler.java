package com.apixandru.rummikub.client.waiting;

import com.apixandru.rummikub.api.room.RummikubRoomListener;
import com.apixandru.rummikub.brotocol.PacketHandler;
import com.apixandru.rummikub.brotocol.connect.server.PacketPlayerJoined;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 01, 2016
 */
public final class PlayerJoinedHandler implements PacketHandler<PacketPlayerJoined> {

    private static final Logger log = LoggerFactory.getLogger(PlayerJoinedHandler.class);

    private final Supplier<RummikubRoomListener> waitingRoomListeners;

    public PlayerJoinedHandler(final Supplier<RummikubRoomListener> waitingRoomListeners) {
        this.waitingRoomListeners = waitingRoomListeners;
    }

    @Override
    public void handle(final PacketPlayerJoined packet) {
        log.info("Player {} joined.", packet.playerName);
        waitingRoomListeners.get().playerJoined(packet.playerName);
    }

}
