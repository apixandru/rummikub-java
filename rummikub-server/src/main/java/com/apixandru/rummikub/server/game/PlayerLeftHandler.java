package com.apixandru.rummikub.server.game;

import com.apixandru.rummikub.brotocol.PacketHandler;
import com.apixandru.rummikub.brotocol.connect.client.PacketLeave;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Alexandru-Constantin Bledea
 * @since May 04, 2016
 */
public class PlayerLeftHandler implements PacketHandler<PacketLeave> {

    private static final Logger log = LoggerFactory.getLogger(PlayerLeftHandler.class);

    private final AtomicBoolean continueReading;

    public PlayerLeftHandler(final AtomicBoolean continueReading) {
        this.continueReading = continueReading;
    }

    @Override
    public void handle(PacketLeave packet) {
        log.debug("Player left.");
        continueReading.set(false);
    }

}

