package com.apixandru.rummikub.client.game;

import com.apixandru.games.rummikub.api.BoardListener;
import com.apixandru.games.rummikub.api.GameEventListener;
import com.apixandru.games.rummikub.api.Player;
import com.apixandru.games.rummikub.api.PlayerCallback;
import com.apixandru.games.rummikub.brotocol.PacketWriter;
import com.apixandru.rummikub.client.ClientPacketHandler;
import com.apixandru.rummikub.game.GameConfigurer;

/**
 * @author Alexandru-Constantin Bledea
 * @since Apr 18, 2016
 */
public class ClientGameConfigurer implements GameConfigurer {

    private final ClientPacketHandler clientPacketHandler;
    private final PacketWriter packetWriter;

    public ClientGameConfigurer(final ClientPacketHandler clientPacketHandler, final PacketWriter packetWriter) {
        this.clientPacketHandler = clientPacketHandler;
        this.packetWriter = packetWriter;
    }

    @Override
    public void addGameEventListener(final GameEventListener gameEventListener) {
        clientPacketHandler.setGameEventListener(gameEventListener);
    }

    @Override
    public void addBoardListener(final BoardListener boardListener) {
        clientPacketHandler.setBoardListener(boardListener);
    }

    @Override
    public Player<Integer> newPlayer(final PlayerCallback<Integer> playerCallback) {
        clientPacketHandler.setPlayerCallback(playerCallback);
        return new SocketPlayer(playerCallback.getPlayerName(), packetWriter);
    }

}