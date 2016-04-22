package com.apixandru.rummikub.server.game;

import com.apixandru.rummikub.api.Card;
import com.apixandru.rummikub.api.PlayerCallback;
import com.apixandru.rummikub.brotocol.PacketWriter;
import com.apixandru.rummikub.brotocol.game.server.PacketReceiveCard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 16, 2016
 */
public class ServerPlayerCallback implements PlayerCallback<Integer> {

    private static final Logger log = LoggerFactory.getLogger(ServerPlayerCallback.class);

    private final String playerName;

    private final PacketWriter packetWriter;

    public ServerPlayerCallback(final String playerName, final PacketWriter packetWriter) {
        this.playerName = playerName;
        this.packetWriter = packetWriter;
    }


    @Override
    public String getPlayerName() {
        return playerName;
    }

    @Override
    public void cardReceived(final Card card, final Integer hint) {
        log.debug("[{}] Sending cardReceived(card={}, hint={})", playerName, card, hint);
        final PacketReceiveCard packet = new PacketReceiveCard();
        packet.card = card;
        packet.hint = hint;
        packetWriter.writePacket(packet);
    }

}
