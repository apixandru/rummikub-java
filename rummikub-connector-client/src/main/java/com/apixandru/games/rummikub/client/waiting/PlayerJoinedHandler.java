package com.apixandru.games.rummikub.client.waiting;

import com.apixandru.games.rummikub.brotocol.PacketHandler;
import com.apixandru.games.rummikub.brotocol.connect.server.PacketPlayerJoined;
import com.apixandru.rummikub.waiting.WaitingRoomListener;

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
//        waitingRoomListeners.get().playerJoined(packet.playerName);
    }

}
