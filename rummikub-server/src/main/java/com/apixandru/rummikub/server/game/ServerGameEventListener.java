package com.apixandru.rummikub.server.game;

import com.apixandru.rummikub.api.GameEventListener;
import com.apixandru.rummikub.api.GameOverReason;
import com.apixandru.rummikub.brotocol.PacketWriter;
import com.apixandru.rummikub.brotocol.game.server.PacketGameOver;
import com.apixandru.rummikub.brotocol.game.server.PacketNewTurn;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 16, 2016
 */
public class ServerGameEventListener implements GameEventListener {

    private static final Logger log = LoggerFactory.getLogger(ServerGameEventListener.class);

    private final String playerName;
    private final PacketWriter packetWriter;

    public ServerGameEventListener(final String playerName, final PacketWriter packetWriter) {
        this.playerName = playerName;
        this.packetWriter = packetWriter;
    }

    @Override
    public void newTurn(final String player) {
        log.debug("[{}] Sending newTurn(player={})", playerName, player);
        final PacketNewTurn packet = new PacketNewTurn();
        packet.playerName = player;
        packetWriter.writePacket(packet);
    }

    @Override
    public void gameOver(final String player, final GameOverReason reason) {
        log.debug("[{}] Sending gameOver(player={}, reason={})", playerName, player, reason);
        final PacketGameOver packet = new PacketGameOver();
        packet.player = player;
        packet.reason = reason;
        packetWriter.writePacket(packet);
    }

}
