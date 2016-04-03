package com.apixandru.games.rummikub.client.game;

import com.apixandru.games.rummikub.api.BoardCallback;
import com.apixandru.games.rummikub.api.Card;
import com.apixandru.games.rummikub.brotocol.PacketHandler;
import com.apixandru.games.rummikub.brotocol.game.server.PacketCardPlaced;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 03, 2016
 */
public class CardPlacedHandler implements PacketHandler<PacketCardPlaced> {

    private static final Logger log = LoggerFactory.getLogger(CardPlacedHandler.class);
    private final BoardCallback boardCallback;

    public CardPlacedHandler(final BoardCallback boardCallback) {
        this.boardCallback = boardCallback;
    }

    @Override
    public void handle(final PacketCardPlaced packet) {
        final Card card = packet.card;
        final int x = packet.x;
        final int y = packet.y;
        log.debug("Received onCardPlacedOnBoard(card={}, x={}, y={})", card, x, y);
        boardCallback.onCardPlacedOnBoard(card, x, y);

    }
}
