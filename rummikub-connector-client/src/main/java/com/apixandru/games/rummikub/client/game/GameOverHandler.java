package com.apixandru.games.rummikub.client.game;

import com.apixandru.games.rummikub.api.GameEventListener;
import com.apixandru.games.rummikub.api.GameOverReason;
import com.apixandru.games.rummikub.brotocol.PacketHandler;
import com.apixandru.games.rummikub.brotocol.game.server.PacketGameOver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 03, 2016
 */
public class GameOverHandler implements PacketHandler<PacketGameOver> {

    private static final Logger log = LoggerFactory.getLogger(GameOverHandler.class);

    private final Supplier<GameEventListener> gameEventListeners;
    private final AtomicBoolean continueReading;

    public GameOverHandler(final Supplier<GameEventListener> gameEventListeners, final AtomicBoolean continueReading) {
        this.gameEventListeners = gameEventListeners;
        this.continueReading = continueReading;
    }

    @Override
    public void handle(final PacketGameOver packet) {
        final String player = packet.player;
        final GameOverReason reason = packet.reason;

        log.debug("Received gameOver(player={}, reason={})", player, reason);

        continueReading.set(false);

        gameEventListeners.get().gameOver(player, reason);

    }

}
