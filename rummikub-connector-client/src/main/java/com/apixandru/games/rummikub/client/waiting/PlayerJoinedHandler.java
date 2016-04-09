package com.apixandru.games.rummikub.client.waiting;

import com.apixandru.games.rummikub.brotocol.PacketHandler;
import com.apixandru.games.rummikub.brotocol.connect.server.PacketPlayerJoined;
import com.apixandru.rummikub.waiting.WaitingRoomListener;

import java.util.List;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 01, 2016
 */
public final class PlayerJoinedHandler implements PacketHandler<PacketPlayerJoined> {

    private final List<WaitingRoomListener> waitingRoomListeners;

    public PlayerJoinedHandler(final List<WaitingRoomListener> waitingRoomListeners) {
        this.waitingRoomListeners = waitingRoomListeners;
    }

    @Override
    public void handle(final PacketPlayerJoined packet) {
        waitingRoomListeners.forEach(listener -> listener.playerJoined(packet.playerName));
    }

}
