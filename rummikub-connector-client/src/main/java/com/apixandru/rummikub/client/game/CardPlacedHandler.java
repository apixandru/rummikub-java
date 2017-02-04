package com.apixandru.rummikub.client.game;

import com.apixandru.rummikub.api.BoardListener;
import com.apixandru.rummikub.api.Card;
import com.apixandru.rummikub.brotocol.PacketHandler;
import com.apixandru.rummikub.brotocol.game.server.PacketCardPlaced;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 03, 2016
 */
public class CardPlacedHandler implements PacketHandler<PacketCardPlaced> {

    private static final Logger log = LoggerFactory.getLogger(CardPlacedHandler.class);
    private final BoardListener boardListener;

    public CardPlacedHandler(final BoardListener boardListener) {
        this.boardListener = boardListener;
    }

    @Override
    public void handle(final PacketCardPlaced packet) {
        final Card card = packet.card;
        final int x = packet.x;
        final int y = packet.y;

        log.debug("Received onCardPlacedOnBoard(card={}, x={}, y={})", card, x, y);

        boardListener.onCardPlacedOnBoard(card, x, y);
    }

}
