package com.apixandru.games.rummikub.client.game;

import com.apixandru.games.rummikub.api.GameEventListener;
import com.apixandru.games.rummikub.api.GameOverReason;
import com.apixandru.games.rummikub.brotocol.PacketHandler;
import com.apixandru.games.rummikub.brotocol.game.server.PacketGameOver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 03, 2016
 */
public class GameOverHandler implements PacketHandler<PacketGameOver> {

    private static final Logger log = LoggerFactory.getLogger(GameOverHandler.class);

    private final List<GameEventListener> gameEventListeners;
    private final AtomicBoolean continueReading;

    public GameOverHandler(final List<GameEventListener> gameEventListeners, final AtomicBoolean continueReading) {
        this.gameEventListeners = gameEventListeners;
        this.continueReading = continueReading;
    }

    @Override
    public void handle(final PacketGameOver packet) {
        final String player = packet.player;
        final GameOverReason reason = packet.reason;
        final boolean me = packet.me;

        log.debug("Received gameOver(player={}, reason={}, me={})", player, reason, me);

        continueReading.set(false);

        gameEventListeners.forEach(listener -> listener.gameOver(player, reason, me));

    }

}
