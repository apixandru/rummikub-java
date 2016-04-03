package com.apixandru.games.rummikub.client.game;

import com.apixandru.games.rummikub.api.BoardCallback;
import com.apixandru.games.rummikub.api.Card;
import com.apixandru.games.rummikub.brotocol.PacketHandler;
import com.apixandru.games.rummikub.brotocol.game.server.PacketCardRemoved;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 03, 2016
 */
public class CardRemovedHandler implements PacketHandler<PacketCardRemoved> {

    private static final Logger log = LoggerFactory.getLogger(CardRemovedHandler.class);
    private final BoardCallback boardCallback;

    public CardRemovedHandler(final BoardCallback boardCallback) {
        this.boardCallback = boardCallback;
    }

    @Override
    public void handle(final PacketCardRemoved packet) {
        final Card card = packet.card;
        final int x = packet.x;
        final int y = packet.y;
        final boolean reset = packet.reset;
        log.debug("Received onCardRemovedFromBoard(card={}, x={}, y={}, reset={})", card, x, y, reset);
        boardCallback.onCardRemovedFromBoard(card, x, y, reset);
    }

}
