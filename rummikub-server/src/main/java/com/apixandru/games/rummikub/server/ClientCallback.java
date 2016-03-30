package com.apixandru.games.rummikub.server;

import com.apixandru.games.rummikub.api.Card;
import com.apixandru.games.rummikub.api.CompoundCallback;
import com.apixandru.games.rummikub.api.GameOverReason;
import com.apixandru.games.rummikub.brotocol.PacketWriter;
import com.apixandru.games.rummikub.brotocol.game.server.PacketCardPlaced;
import com.apixandru.games.rummikub.brotocol.game.server.PacketCardRemoved;
import com.apixandru.games.rummikub.brotocol.game.server.PacketGameOver;
import com.apixandru.games.rummikub.brotocol.game.server.PacketNewTurn;
import com.apixandru.games.rummikub.brotocol.game.server.PacketReceiveCard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 05, 2016
 */
final class ClientCallback implements CompoundCallback<Integer> {

    private static final Logger log = LoggerFactory.getLogger(ClientCallback.class);

    private final PacketWriter packetWriter;

    private final String playerName;

    ClientCallback(final String playerName, final PacketWriter writer) {
        this.packetWriter = writer;
        this.playerName = playerName;
    }

    @Override
    public void cardReceived(final Card card, final Integer hint) {
        log.debug("[{}] Sending cardReceived(card={}, hint={})", playerName, card, hint);
        final PacketReceiveCard packet = new PacketReceiveCard();
        packet.card = card;
        packet.hint = hint;
        packetWriter.writePacket(packet);
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

    @Override
    public void newTurn(final String player, final boolean myTurn) {
        log.debug("[{}] Sending newTurn(player={}, myTurn={})", playerName, player, myTurn);
        final PacketNewTurn packet = new PacketNewTurn();
        packet.myTurn = myTurn;
        packet.playerName = player;
        packetWriter.writePacket(packet);
    }

    @Override
    public void gameOver(final String player, final GameOverReason reason, final boolean me) {
        log.debug("[{}] Sending gameOver(player={}, reason={}, me={})", playerName, player, reason, me);
        final PacketGameOver packet = new PacketGameOver();
        packet.player = player;
        packet.reason = reason;
        packet.me = me;
        packetWriter.writePacket(packet);
    }

}
