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

import static com.apixandru.games.rummikub.brotocol.Brotocol.CLIENT_END_TURN;
import static com.apixandru.games.rummikub.brotocol.Brotocol.CLIENT_MOVE_CARD;
import static com.apixandru.games.rummikub.brotocol.Brotocol.CLIENT_PLACE_CARD;
import static com.apixandru.games.rummikub.brotocol.Brotocol.CLIENT_TAKE_CARD;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 04, 2016
 */
final class SocketPlayer<H> extends AbstractIntWritable implements Player<H> {

    private final List<H> hints;
    private final String playerName;
    private final PacketWriter writer = null; // properly initialize

    /**
     * @param playerName the player name
     * @param writer     the writer
     * @param hints      the client hints
     */
    SocketPlayer(final String playerName, final BroWriter writer, final List<H> hints) {
        super(writer);
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
//        writer.writePacket(packet);
        writeAndFlush(CLIENT_PLACE_CARD, card, x, y);
    }

    @Override
    public void moveCardOnBoard(final Card card, final int fromX, final int fromY, final int toX, final int toY) {
        final PacketMoveCard packet = new PacketMoveCard();
        packet.card = card;
        packet.fromX = fromX;
        packet.fromY = fromY;
        packet.toX = toX;
        packet.toY = toY;
//        writer.writePacket(packet);
        writeAndFlush(CLIENT_MOVE_CARD, card, fromX, fromY, toX, toY);
    }

    @Override
    public void takeCardFromBoard(final Card card, final int x, final int y, final H hint) {
        final PacketTakeCard packet = new PacketTakeCard();
        packet.card = card;
        packet.x = x;
        packet.y = y;
        packet.hint = this.hints.indexOf(hint);
//        writer.writePacket(packet);
        writeAndFlush(CLIENT_TAKE_CARD, card, x, y, this.hints.indexOf(hint));
    }

    @Override
    public void endTurn() {
        final PacketEndTurn packet = new PacketEndTurn();
//        writer.writePacket(packet);
        writeAndFlush(CLIENT_END_TURN);
    }

}
