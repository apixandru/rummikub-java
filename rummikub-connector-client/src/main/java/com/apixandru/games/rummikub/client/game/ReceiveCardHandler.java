package com.apixandru.games.rummikub.client.game;

import com.apixandru.games.rummikub.api.Card;
import com.apixandru.games.rummikub.api.PlayerCallback;
import com.apixandru.games.rummikub.brotocol.PacketHandler;
import com.apixandru.games.rummikub.brotocol.game.server.PacketReceiveCard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 03, 2016
 */
public class ReceiveCardHandler<H> implements PacketHandler<PacketReceiveCard> {

    private static final Logger log = LoggerFactory.getLogger(ReceiveCardHandler.class);

    private final Supplier<PlayerCallback<H>> playerCallbacks;
    private final List<H> hints;

    public ReceiveCardHandler(final Supplier<PlayerCallback<H>> playerCallbacks, final List<H> hints) {
        this.playerCallbacks = playerCallbacks;
        this.hints = new ArrayList<>(hints);
    }

    @Override
    public void handle(final PacketReceiveCard packet) {
        final Card card = packet.card;
        final Integer hintIndex = packet.hint;

        final H hint = null == hintIndex ? null : hints.get(hintIndex);

        log.debug("Received cardReceived(card={}, hintIndex={})", card, hintIndex);

        playerCallbacks.get().cardReceived(card, hint);
    }

}
