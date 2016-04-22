package com.apixandru.rummikub.client.game;

import com.apixandru.rummikub.api.Card;
import com.apixandru.rummikub.api.Player;
import com.apixandru.rummikub.brotocol.PacketWriter;
import com.apixandru.rummikub.brotocol.game.client.PacketEndTurn;
import com.apixandru.rummikub.brotocol.game.client.PacketMoveCard;
import com.apixandru.rummikub.brotocol.game.client.PacketPlaceCard;
import com.apixandru.rummikub.brotocol.game.client.PacketTakeCard;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 04, 2016
 */
final class SocketPlayer implements Player<Integer> {

    private final PacketWriter writer;
    private final String playerName;

    SocketPlayer(final String playerName, final PacketWriter writer) {
        this.writer = writer;
        this.playerName = playerName;
    }

    private static int toInteger(final Integer hint) {
        if (null == hint) {
            return -1;
        }
        return hint;
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
    public void takeCardFromBoard(final Card card, final int x, final int y, final Integer hint) {
        final PacketTakeCard packet = new PacketTakeCard();
        packet.card = card;
        packet.x = x;
        packet.y = y;
        packet.hint = toInteger(hint);
        writer.writePacket(packet);
    }

    @Override
    public void endTurn() {
        final PacketEndTurn packet = new PacketEndTurn();
        writer.writePacket(packet);
    }

}
