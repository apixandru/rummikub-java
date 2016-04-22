package com.apixandru.rummikub.client.game;

import com.apixandru.rummikub.api.GameEventListener;
import com.apixandru.rummikub.brotocol.PacketHandler;
import com.apixandru.rummikub.brotocol.game.server.PacketNewTurn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 03, 2016
 */
public class NewTurnHandler implements PacketHandler<PacketNewTurn> {

    private static final Logger log = LoggerFactory.getLogger(NewTurnHandler.class);

    private final Supplier<GameEventListener> gameEventListeners;

    public NewTurnHandler(final Supplier<GameEventListener> gameEventListeners) {
        this.gameEventListeners = gameEventListeners;
    }

    @Override
    public void handle(final PacketNewTurn packet) {
        final String playerName = packet.playerName;
        log.debug("Received newTurn(player={})", playerName);
        gameEventListeners.get().newTurn(playerName);
    }

}
