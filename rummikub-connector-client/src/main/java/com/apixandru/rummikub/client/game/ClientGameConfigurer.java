package com.apixandru.rummikub.client.game;

import com.apixandru.rummikub.api.GameConfigurer;
import com.apixandru.rummikub.api.GameEventListener;
import com.apixandru.rummikub.api.game.BoardListener;
import com.apixandru.rummikub.api.game.Player;
import com.apixandru.rummikub.api.game.PlayerCallback;
import com.apixandru.rummikub.brotocol.PacketWriter;
import com.apixandru.rummikub.client.ClientPacketHandler;

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
    public void removeGameEventListener(GameEventListener gameEventListener) {
        clientPacketHandler.setGameEventListener(null);
    }

    @Override
    public void addBoardListener(final BoardListener boardListener) {
        clientPacketHandler.setBoardListener(boardListener);
    }

    @Override
    public void removeBoardListener(BoardListener boardListener) {
        clientPacketHandler.setBoardListener(null);
    }

    @Override
    public Player<Integer> newPlayer(final PlayerCallback<Integer> playerCallback) {
        clientPacketHandler.setPlayerCallback(playerCallback);
        return new SocketPlayer(playerCallback.getPlayerName(), packetWriter);
    }

    @Override
    public void removePlayer(Player<Integer> player) {
        clientPacketHandler.setPlayerCallback(null);
    }

}
