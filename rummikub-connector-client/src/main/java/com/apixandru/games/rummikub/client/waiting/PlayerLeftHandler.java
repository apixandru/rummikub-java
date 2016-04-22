package com.apixandru.games.rummikub.client.waiting;

import com.apixandru.games.rummikub.brotocol.PacketHandler;
import com.apixandru.games.rummikub.brotocol.connect.server.PacketPlayerLeft;
import com.apixandru.rummikub.model.waiting.WaitingRoomListener;

import java.util.function.Supplier;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 01, 2016
 */
public final class PlayerLeftHandler implements PacketHandler<PacketPlayerLeft> {

    private final Supplier<WaitingRoomListener> waitingRoomListeners;

    public PlayerLeftHandler(final Supplier<WaitingRoomListener> waitingRoomListeners) {
        this.waitingRoomListeners = waitingRoomListeners;
    }

    @Override
    public void handle(final PacketPlayerLeft packet) {
        waitingRoomListeners.get().playerLeft(packet.playerName);
    }

}
