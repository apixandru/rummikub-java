package com.apixandru.rummikub.server.game;

import com.apixandru.rummikub.api.game.Player;
import com.apixandru.rummikub.brotocol.PacketHandler;
import com.apixandru.rummikub.brotocol.game.client.PacketMoveCard;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 05, 2016
 */
public class MoveCardHandler implements PacketHandler<PacketMoveCard> {

    private static final Logger log = LoggerFactory.getLogger(MoveCardHandler.class);

    private final Player<Integer> player;

    public MoveCardHandler(final Player<Integer> player) {
        this.player = player;
    }

    @Override
    public void handle(final PacketMoveCard packet) {
        final int fromX = packet.fromX;
        final int fromY = packet.fromY;
        final int toX = packet.toX;
        final int toY = packet.toY;
        log.debug("[{}] Received moveCardOnBoard(fromX={}, fromY={}, toX={}, toY={})", player.getName(), fromX, fromY, toX, toY);
        player.moveCardOnBoard(fromX, fromY, toX, toY);
    }

}
