package com.apixandru.rummikub.server.game;

import com.apixandru.rummikub.api.Player;
import com.apixandru.rummikub.brotocol.PacketHandler;
import com.apixandru.rummikub.brotocol.game.client.PacketEndTurn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Alexandru-Constantin Bledea
 * @since March 31, 2016
 */
public class EndTurnHandler implements PacketHandler<PacketEndTurn> {

    private static final Logger log = LoggerFactory.getLogger(EndTurnHandler.class);

    private final Player<Integer> player;
    private final String playerName;

    public EndTurnHandler(String playerName, final Player<Integer> player) {
        this.player = player;
        this.playerName = playerName;
    }

    @Override
    public void handle(final PacketEndTurn packet) {
        log.debug("[{}] Received endTurn()", playerName);
        player.endTurn();
    }

}
