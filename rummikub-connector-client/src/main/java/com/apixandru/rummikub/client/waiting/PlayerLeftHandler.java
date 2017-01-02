package com.apixandru.rummikub.client.waiting;

import com.apixandru.rummikub.api.room.RummikubRoomListener;
import com.apixandru.rummikub.brotocol.PacketHandler;
import com.apixandru.rummikub.brotocol.connect.server.PacketPlayerLeft;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 01, 2016
 */
public final class PlayerLeftHandler implements PacketHandler<PacketPlayerLeft> {

    private static final Logger log = LoggerFactory.getLogger(PlayerLeftHandler.class);

    private final Supplier<RummikubRoomListener> waitingRoomListeners;

    public PlayerLeftHandler(final Supplier<RummikubRoomListener> waitingRoomListeners) {
        this.waitingRoomListeners = waitingRoomListeners;
    }

    @Override
    public void handle(final PacketPlayerLeft packet) {
        log.info("Player {} left.", packet.playerName);
        waitingRoomListeners.get().playerLeft(packet.playerName);
    }

}
