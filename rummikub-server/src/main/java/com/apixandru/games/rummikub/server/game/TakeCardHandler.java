package com.apixandru.games.rummikub.server.game;

import com.apixandru.games.rummikub.api.Card;
import com.apixandru.games.rummikub.api.Player;
import com.apixandru.games.rummikub.brotocol.PacketHandler;
import com.apixandru.games.rummikub.brotocol.game.client.PacketTakeCard;
import com.apixandru.games.rummikub.server.PlayerProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 05, 2016
 */
public class TakeCardHandler implements PacketHandler<PacketTakeCard> {

    private static final Logger log = LoggerFactory.getLogger(TakeCardHandler.class);

    private final PlayerProvider<Integer> playerProvider;

    public TakeCardHandler(final PlayerProvider<Integer> playerProvider) {
        this.playerProvider = playerProvider;
    }

    @Override
    public void handle(final PacketTakeCard packet) {
        final Card card = packet.card;
        final int x = packet.x;
        final int y = packet.y;
        final int hint = packet.hint;

        final Player<Integer> player = playerProvider.get();

        log.debug("[{}] Received takeCardFromBoard(card={}, x={}, y={}, hint={})", player.getName(), card, x, y, hint);
        player.takeCardFromBoard(card, x, y, hint);
    }

}
