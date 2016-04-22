package com.apixandru.games.rummikub.server.game;

import com.apixandru.rummikub.api.Card;
import com.apixandru.rummikub.api.Player;
import com.apixandru.rummikub.brotocol.PacketHandler;
import com.apixandru.rummikub.brotocol.game.client.PacketPlaceCard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 05, 2016
 */
public class PlaceCardOnBoardHandler implements PacketHandler<PacketPlaceCard> {

    private static final Logger log = LoggerFactory.getLogger(EndTurnHandler.class);

    private final Supplier<Player<Integer>> playerProvider;

    public PlaceCardOnBoardHandler(final Supplier<Player<Integer>> playerProvider) {
        this.playerProvider = playerProvider;
    }

    @Override
    public void handle(final PacketPlaceCard packet) {
        final Card card = packet.card;
        final int x = packet.x;
        final int y = packet.y;

        final Player<Integer> player = playerProvider.get();

        log.debug("[{}] Received placeCardOnBoard(card={}, x={}, y={})", player.getName(), card, x, y);
        player.placeCardOnBoard(card, x, y);
    }

}
