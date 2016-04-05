package com.apixandru.games.rummikub.server.game;

import com.apixandru.games.rummikub.api.Card;
import com.apixandru.games.rummikub.api.Player;
import com.apixandru.games.rummikub.brotocol.PacketHandler;
import com.apixandru.games.rummikub.brotocol.game.client.PacketMoveCard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 05, 2016
 */
public class MoveCardHandler implements PacketHandler<PacketMoveCard> {

    private static final Logger log = LoggerFactory.getLogger(MoveCardHandler.class);

    private final Player<Integer> player;
    private final String playerName;

    public MoveCardHandler(final Player<Integer> player, final String playerName) {
        this.player = player;
        this.playerName = playerName;
    }

    @Override
    public void handle(final PacketMoveCard packet) {
        final Card card = packet.card;
        final int fromX = packet.fromX;
        final int fromY = packet.fromY;
        final int toX = packet.toX;
        final int toY = packet.toY;
        log.debug("[{}] Received moveCardOnBoard(card={}, fromX={}, fromY={}, toX={}, toY={})", playerName, card, fromX, fromY, toX, toY);
        player.moveCardOnBoard(card, fromX, fromY, toX, toY);
    }

}
