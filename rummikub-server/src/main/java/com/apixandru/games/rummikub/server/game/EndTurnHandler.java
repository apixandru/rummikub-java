package com.apixandru.games.rummikub.server.game;

import com.apixandru.games.rummikub.api.Player;
import com.apixandru.games.rummikub.brotocol.PacketHandler;
import com.apixandru.games.rummikub.brotocol.game.client.PacketEndTurn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

/**
 * @author Alexandru-Constantin Bledea
 * @since March 31, 2016
 */
public class EndTurnHandler implements PacketHandler<PacketEndTurn> {

    private static final Logger log = LoggerFactory.getLogger(EndTurnHandler.class);

    private final Supplier<Player<Integer>> playerProvider;

    public EndTurnHandler(final Supplier<Player<Integer>> playerProvider) {
        this.playerProvider = playerProvider;
    }

    @Override
    public void handle(final PacketEndTurn packet) {
        final Player<Integer> player = playerProvider.get();

        log.debug("[{}] Received endTurn()", player.getName());
        player.endTurn();
    }

}
