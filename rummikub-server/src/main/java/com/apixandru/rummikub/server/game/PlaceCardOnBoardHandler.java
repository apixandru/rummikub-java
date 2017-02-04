package com.apixandru.rummikub.server.game;

import com.apixandru.rummikub.api.Card;
import com.apixandru.rummikub.api.Player;
import com.apixandru.rummikub.brotocol.PacketHandler;
import com.apixandru.rummikub.brotocol.game.client.PacketPlaceCard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 05, 2016
 */
public class PlaceCardOnBoardHandler implements PacketHandler<PacketPlaceCard> {

    private static final Logger log = LoggerFactory.getLogger(EndTurnHandler.class);

    private final Player<Integer> player;

    public PlaceCardOnBoardHandler(final Player<Integer> player) {
        this.player = player;
    }

    @Override
    public void handle(final PacketPlaceCard packet) {
        final Card card = packet.card;
        final int x = packet.x;
        final int y = packet.y;

        log.debug("[{}] Received placeCardOnBoard(card={}, x={}, y={})", player.getName(), card, x, y);
        player.placeCardOnBoard(card, x, y);
    }

}
