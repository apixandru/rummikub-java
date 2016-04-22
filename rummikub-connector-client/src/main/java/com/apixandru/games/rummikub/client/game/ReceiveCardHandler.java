package com.apixandru.games.rummikub.client.game;

import com.apixandru.games.rummikub.brotocol.PacketHandler;
import com.apixandru.games.rummikub.brotocol.game.server.PacketReceiveCard;
import com.apixandru.rummikub.api.Card;
import com.apixandru.rummikub.api.PlayerCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 03, 2016
 */
public class ReceiveCardHandler implements PacketHandler<PacketReceiveCard> {

    private static final Logger log = LoggerFactory.getLogger(ReceiveCardHandler.class);

    private final Supplier<PlayerCallback<Integer>> playerCallbacks;

    public ReceiveCardHandler(final Supplier<PlayerCallback<Integer>> playerCallbacks) {
        this.playerCallbacks = playerCallbacks;
    }

    @Override
    public void handle(final PacketReceiveCard packet) {
        final Card card = packet.card;
        final Integer hintIndex = packet.hint;

        log.debug("Received cardReceived(card={}, hintIndex={})", card, hintIndex);

        playerCallbacks.get().cardReceived(card, packet.hint);
    }

}
