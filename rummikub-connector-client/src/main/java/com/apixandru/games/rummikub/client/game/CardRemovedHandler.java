package com.apixandru.games.rummikub.client.game;

import com.apixandru.games.rummikub.brotocol.PacketHandler;
import com.apixandru.games.rummikub.brotocol.game.server.PacketCardRemoved;
import com.apixandru.rummikub.api.BoardListener;
import com.apixandru.rummikub.api.Card;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 03, 2016
 */
public class CardRemovedHandler implements PacketHandler<PacketCardRemoved> {

    private static final Logger log = LoggerFactory.getLogger(CardRemovedHandler.class);

    private final Supplier<BoardListener> boardListener;

    public CardRemovedHandler(final Supplier<BoardListener> boardListener) {
        this.boardListener = boardListener;
    }

    @Override
    public void handle(final PacketCardRemoved packet) {
        final Card card = packet.card;
        final int x = packet.x;
        final int y = packet.y;
        final boolean reset = packet.reset;

        log.debug("Received onCardRemovedFromBoard(card={}, x={}, y={}, reset={})", card, x, y, reset);

        boardListener.get().onCardRemovedFromBoard(card, x, y, reset);
    }

}
