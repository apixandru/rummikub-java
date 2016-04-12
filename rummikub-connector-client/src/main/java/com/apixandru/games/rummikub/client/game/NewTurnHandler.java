package com.apixandru.games.rummikub.client.game;

import com.apixandru.games.rummikub.api.GameEventListener;
import com.apixandru.games.rummikub.brotocol.PacketHandler;
import com.apixandru.games.rummikub.brotocol.game.server.PacketNewTurn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 03, 2016
 */
public class NewTurnHandler implements PacketHandler<PacketNewTurn> {

    private static final Logger log = LoggerFactory.getLogger(NewTurnHandler.class);

    private final Collection<GameEventListener> gameEventListeners;

    public NewTurnHandler(final Collection<GameEventListener> gameEventListeners) {
        this.gameEventListeners = gameEventListeners;
    }

    @Override
    public void handle(final PacketNewTurn packet) {
        final String playerName = packet.playerName;
        log.debug("Received newTurn(player={})", playerName);
        gameEventListeners.forEach(listener -> listener.newTurn(playerName));
    }

}
