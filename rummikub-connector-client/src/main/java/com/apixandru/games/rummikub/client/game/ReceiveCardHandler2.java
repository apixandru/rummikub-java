package com.apixandru.games.rummikub.client.game;

import com.apixandru.games.rummikub.api.Card;
import com.apixandru.games.rummikub.api.PlayerCallback;
import com.apixandru.games.rummikub.brotocol.PacketHandler;
import com.apixandru.games.rummikub.brotocol.game.server.PacketReceiveCard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Supplier;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 03, 2016
 */
public class ReceiveCardHandler2 implements PacketHandler<PacketReceiveCard> {

    private static final Logger log = LoggerFactory.getLogger(ReceiveCardHandler2.class);

    private final Supplier<PlayerCallback<Integer>> playerCallbacks;

    public ReceiveCardHandler2(final Supplier<PlayerCallback<Integer>> playerCallbacks) {
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
