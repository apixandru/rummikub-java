package com.apixandru.rummikub.client.waiting;

import com.apixandru.rummikub.api.WaitingRoomListener;
import com.apixandru.rummikub.brotocol.PacketHandler;
import com.apixandru.rummikub.brotocol.connect.server.PacketPlayerJoined;

import java.util.function.Supplier;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 01, 2016
 */
public final class PlayerJoinedHandler implements PacketHandler<PacketPlayerJoined> {

    private final Supplier<WaitingRoomListener> waitingRoomListeners;

    public PlayerJoinedHandler(final Supplier<WaitingRoomListener> waitingRoomListeners) {
        this.waitingRoomListeners = waitingRoomListeners;
    }

    @Override
    public void handle(final PacketPlayerJoined packet) {
        waitingRoomListeners.get().playerJoined(packet.playerName);
    }

}