package com.apixandru.rummikub.server.waiting;

import com.apixandru.rummikub.brotocol.PacketHandler;
import com.apixandru.rummikub.brotocol.connect.client.PacketLeave;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 08, 2016
 */
public class LeaveHandler implements PacketHandler<PacketLeave> {

    private final AtomicBoolean continueGame;

    public LeaveHandler(final AtomicBoolean continueGame) {
        this.continueGame = continueGame;
    }

    @Override
    public void handle(final PacketLeave packet) {
        this.continueGame.set(false);
    }

}
