package com.apixandru.games.rummikub.client.game;

import com.apixandru.games.rummikub.api.GameEventListener;
import com.apixandru.games.rummikub.brotocol.PacketHandler;
import com.apixandru.games.rummikub.brotocol.game.server.PacketNewTurn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public class NewTurnHandler implements PacketHandler<PacketNewTurn> {

    private static final Logger log = LoggerFactory.getLogger(NewTurnHandler.class);

    private final Collection<GameEventListener> gameEventListeners;

    public NewTurnHandler(final Collection<GameEventListener> gameEventListeners) {
        this.gameEventListeners = gameEventListeners;
    }

    @Override
    public void handle(final PacketNewTurn packet) {
        final boolean myTurn = packet.myTurn;
        final String playerName = packet.playerName;
        log.debug("Received newTurn(player={}, myTurn={})", playerName, myTurn);
        gameEventListeners.forEach(listener -> listener.newTurn(playerName, myTurn));
    }

}
