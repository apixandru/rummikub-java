package com.apixandru.rummikub.server;

import com.apixandru.games.rummikub.brotocol.SocketWrapper;
import com.apixandru.games.rummikub.brotocol.connect.server.PacketPlayerStart;
import com.apixandru.games.rummikub.brotocol.util.SocketPacketProcessor;
import com.apixandru.rummikub.StateChangeListener;
import com.apixandru.rummikub.game.GameConfigurer;
import com.apixandru.rummikub.server.game.ServerBoardListener;
import com.apixandru.rummikub.server.game.ServerGameEventListener;
import com.apixandru.rummikub.server.game.ServerPlayerCallback;
import com.apixandru.rummikub.server.waiting.ServerWaitingRoomListener;
import com.apixandru.rummikub.waiting.WaitingRoomConfigurer;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 13, 2016
 */
class ServerStateChangeListener implements StateChangeListener, Runnable {

    private final SocketWrapper socketWrapper;
    private final String playerName;
    private final ServerPacketHandler serverPacketHandler;
    private final SocketPacketProcessor socketPacketProcessor;

    ServerStateChangeListener(final String playerName, final SocketWrapper socketWrapper) {
        this.playerName = playerName;
        this.socketWrapper = socketWrapper;

        this.serverPacketHandler = new ServerPacketHandler();
        this.socketPacketProcessor = new SocketPacketProcessor(this.socketWrapper, this.serverPacketHandler);
    }

    @Override
    public void enteredWaitingRoom(final WaitingRoomConfigurer configurer) {
        serverPacketHandler.reset();

        configurer.registerListener(new ServerWaitingRoomListener(socketWrapper));
        serverPacketHandler.setStartGameListenerProvider(configurer);
    }

    @Override
    public void enteredGame(final GameConfigurer configurer) {
        serverPacketHandler.reset();
        socketWrapper.writePacket(new PacketPlayerStart());
        configurer.addBoardListener(new ServerBoardListener(playerName, socketWrapper));
        configurer.addGameEventListener(new ServerGameEventListener(playerName, socketWrapper));
        serverPacketHandler.setPlayer(configurer.newPlayer(new ServerPlayerCallback(playerName, socketWrapper)));
    }

    @Override
    public void run() {
        this.socketPacketProcessor.run();
    }

}
