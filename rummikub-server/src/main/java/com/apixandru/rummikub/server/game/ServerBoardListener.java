package com.apixandru.rummikub.server.game;

import com.apixandru.games.rummikub.api.BoardCallback;
import com.apixandru.games.rummikub.api.Card;
import com.apixandru.games.rummikub.brotocol.PacketWriter;
import com.apixandru.games.rummikub.brotocol.game.server.PacketCardPlaced;
import com.apixandru.games.rummikub.brotocol.game.server.PacketCardRemoved;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 16, 2016
 */
public class ServerBoardListener implements BoardCallback {

    private static final Logger log = LoggerFactory.getLogger(ServerBoardListener.class);

    private final String playerName;
    private final PacketWriter packetWriter;

    public ServerBoardListener(final String playerName, final PacketWriter packetWriter) {
        this.playerName = playerName;
        this.packetWriter = packetWriter;
    }

    @Override
    public void onCardPlacedOnBoard(final Card card, final int x, final int y) {
        log.debug("[{}] Sending onCardPlacedOnBoard(card={}, x={}, y={})", playerName, card, x, y);

        final PacketCardPlaced packet = new PacketCardPlaced();
        packet.card = card;
        packet.x = x;
        packet.y = y;

        packetWriter.writePacket(packet);
    }

    @Override
    public void onCardRemovedFromBoard(final Card card, final int x, final int y, final boolean reset) {
        log.debug("[{}] Sending onCardRemovedFromBoard(card={}, x={}, y={}, reset={})", playerName, card, x, y, reset);

        final PacketCardRemoved packet = new PacketCardRemoved();
        packet.card = card;
        packet.x = x;
        packet.y = y;
        packet.reset = reset;

        packetWriter.writePacket(packet);
    }

}
