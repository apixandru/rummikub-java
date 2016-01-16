package com.apixandru.games.rummikub.client;

import com.apixandru.games.rummikub.api.Card;
import com.apixandru.games.rummikub.api.Player;
import com.apixandru.games.rummikub.brotocol.BroWriter;
import com.apixandru.games.rummikub.brotocol.PacketWriter;
import com.apixandru.games.rummikub.brotocol.client.PacketEndTurn;
import com.apixandru.games.rummikub.brotocol.client.PacketMoveCard;
import com.apixandru.games.rummikub.brotocol.client.PacketPlaceCard;
import com.apixandru.games.rummikub.brotocol.client.PacketTakeCard;
import com.apixandru.games.rummikub.brotocol.util.AbstractIntWritable;

import java.util.List;

/**
 * @param <H> hint type
 * @author Alexandru-Constantin Bledea
 * @since January 04, 2016
 */
final class SocketPlayer<H> extends AbstractIntWritable implements Player<H> {

    private final PacketWriter writer;
    private final String playerName;
    private final List<H> hints;

    /**
     * @param playerName the player name
     * @param writer     the writer
     * @param hints      the client hints
     */
    SocketPlayer(final String playerName, final BroWriter writer, final List<H> hints) {
        super(writer);
        this.writer = writer;
        this.hints = hints;
        this.playerName = playerName;
    }

    @Override
    public String getName() {
        return this.playerName;
    }

    @Override
    public void placeCardOnBoard(final Card card, final int x, final int y) {
        final PacketPlaceCard packet = new PacketPlaceCard();
        packet.card = card;
        packet.x = x;
        packet.y = y;
        writer.writePacket(packet);
    }

    @Override
    public void moveCardOnBoard(final Card card, final int fromX, final int fromY, final int toX, final int toY) {
        final PacketMoveCard packet = new PacketMoveCard();
        packet.card = card;
        packet.fromX = fromX;
        packet.fromY = fromY;
        packet.toX = toX;
        packet.toY = toY;
        writer.writePacket(packet);
    }

    @Override
    public void takeCardFromBoard(final Card card, final int x, final int y, final H hint) {
        final PacketTakeCard packet = new PacketTakeCard();
        packet.card = card;
        packet.x = x;
        packet.y = y;
        packet.hint = this.hints.indexOf(hint);
        writer.writePacket(packet);
    }

    @Override
    public void endTurn() {
        final PacketEndTurn packet = new PacketEndTurn();
        writer.writePacket(packet);
    }

}
