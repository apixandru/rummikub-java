package com.apixandru.rummikub.server.game;

import com.apixandru.rummikub.api.game.Card;
import com.apixandru.rummikub.api.game.Player;
import com.apixandru.rummikub.brotocol.PacketHandler;
import com.apixandru.rummikub.brotocol.game.client.PacketTakeCard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 05, 2016
 */
public class TakeCardHandler implements PacketHandler<PacketTakeCard> {

    private static final Logger log = LoggerFactory.getLogger(TakeCardHandler.class);

    private final Supplier<Player<Integer>> playerProvider;

    public TakeCardHandler(final Supplier<Player<Integer>> playerProvider) {
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
