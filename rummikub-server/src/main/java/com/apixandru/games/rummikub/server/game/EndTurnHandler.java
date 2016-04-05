package com.apixandru.games.rummikub.server.game;

import com.apixandru.games.rummikub.api.Player;
import com.apixandru.games.rummikub.brotocol.PacketHandler;
import com.apixandru.games.rummikub.brotocol.game.client.PacketEndTurn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Alexandru-Constantin Bledea
 * @since March 31, 2016
 */
public class EndTurnHandler implements PacketHandler<PacketEndTurn> {

    private static final Logger log = LoggerFactory.getLogger(EndTurnHandler.class);

    private final Player<Integer> player;

    public EndTurnHandler(final Player<Integer> player) {
        this.player = player;
    }

    @Override
    public void handle(final PacketEndTurn packet) {
        log.debug("[{}] Received endTurn()", player.getName());
        player.endTurn();
    }

}
