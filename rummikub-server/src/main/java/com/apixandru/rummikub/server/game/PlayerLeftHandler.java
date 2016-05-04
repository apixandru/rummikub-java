package com.apixandru.rummikub.server.game;

import com.apixandru.rummikub.api.Player;
import com.apixandru.rummikub.brotocol.PacketHandler;
import com.apixandru.rummikub.brotocol.connect.client.PacketLeave;
import com.apixandru.rummikub.brotocol.util.Reference;
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
    private final Reference<Player<Integer>> playerProvider;

    public PlayerLeftHandler(Reference<Player<Integer>> playerProvider, final AtomicBoolean continueReading) {
        this.playerProvider = playerProvider;
        this.continueReading = continueReading;
    }

    @Override
    public void handle(PacketLeave packet) {
        log.debug("{} left.", playerProvider.get().getName());
        continueReading.set(false);
    }

}

