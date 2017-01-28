package com.apixandru.rummikub.client.game;

import com.apixandru.rummikub.api.game.GameEventListener;
import com.apixandru.rummikub.api.game.GameOverReason;
import com.apixandru.rummikub.brotocol.PacketHandler;
import com.apixandru.rummikub.brotocol.game.server.PacketGameOver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 03, 2016
 */
public class GameOverHandler implements PacketHandler<PacketGameOver> {

    private static final Logger log = LoggerFactory.getLogger(GameOverHandler.class);

    private final Collection<GameEventListener> gameEventListeners;
    private final AtomicBoolean continueReading;

    public GameOverHandler(final Collection<GameEventListener> gameEventListeners, final AtomicBoolean continueReading) {
        this.gameEventListeners = gameEventListeners;
        this.continueReading = continueReading;
    }

    @Override
    public void handle(final PacketGameOver packet) {
        final String player = packet.player;
        final GameOverReason reason = packet.reason;

        log.debug("Received gameOver(player={}, reason={})", player, reason);

        continueReading.set(false);

        gameEventListeners.forEach(listener -> listener.gameOver(player, reason));
    }

}
