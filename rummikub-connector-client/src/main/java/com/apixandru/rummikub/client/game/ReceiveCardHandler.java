package com.apixandru.rummikub.client.game;

import com.apixandru.rummikub.api.game.Card;
import com.apixandru.rummikub.api.game.PlayerCallback;
import com.apixandru.rummikub.brotocol.PacketHandler;
import com.apixandru.rummikub.brotocol.game.server.PacketReceiveCard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 03, 2016
 */
public class ReceiveCardHandler implements PacketHandler<PacketReceiveCard> {

    private static final Logger log = LoggerFactory.getLogger(ReceiveCardHandler.class);

    private final PlayerCallback<Integer> playerCallbacks;

    public ReceiveCardHandler(final PlayerCallback<Integer> playerCallbacks) {
        this.playerCallbacks = playerCallbacks;
    }

    @Override
    public void handle(final PacketReceiveCard packet) {
        final Card card = packet.card;
        final Integer hintIndex = packet.hint;

        log.debug("Received cardReceived(card={}, hintIndex={})", card, hintIndex);

        playerCallbacks.cardReceived(card, packet.hint);
    }

}
