package com.apixandru.games.rummikub.client;

import com.apixandru.games.rummikub.brotocol.SocketWrapper;
import com.apixandru.games.rummikub.brotocol.util.SocketPacketProcessor;
import com.apixandru.rummikub.StateChangeListener;
import com.apixandru.rummikub.client.ClientPacketHandler;
import com.apixandru.rummikub.client.game.ClientGameConfigurer;
import com.apixandru.rummikub.client.waiting.ClientWaitingRoomConfigurer;
import com.apixandru.rummikub.game.GameConfigurerAdapter;
import com.apixandru.rummikub.waiting.WaitingRoomConfigurer;
import com.apixandru.rummikub.waiting.WaitingRoomListener;

import static com.apixandru.games.rummikub.brotocol.Brotocols.USE_NEW_IMPLEMENTATION;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 02, 2016
 */
public final class RummikubConnector {

    final SocketWrapper socketWrapper;
    final StateChangeListener stateChangeListener;
    private final ClientPacketHandler packetHandler = new ClientPacketHandler();
    private final SocketPacketProcessor socketPacketProcessor;

    public RummikubConnector(final SocketWrapper socketWrapper, final StateChangeListener stateChangeListener) {
        this.socketWrapper = socketWrapper;
        this.stateChangeListener = stateChangeListener;

        this.socketPacketProcessor = new SocketPacketProcessor(this.socketWrapper, this.packetHandler);
    }

    public void connect() {
        if (USE_NEW_IMPLEMENTATION) {
            newConnect();
        } else {
            oldConnect();
        }
    }

    private void newConnect() {
        packetHandler.setStartGameListener(() -> stateChangeListener.enteredGame(new ClientGameConfigurer(packetHandler, this.socketWrapper)));
        ClientWaitingRoomConfigurer waitingRoomConfigurer = new ClientWaitingRoomConfigurer(packetHandler, socketWrapper);
        stateChangeListener.enteredWaitingRoom(waitingRoomConfigurer);
        new Thread(socketPacketProcessor).start();
    }

    private void oldConnect() {
        stateChangeListener.enteredWaitingRoom(new WaitingRoomConfigurer() {
            @Override
            public void registerListener(final WaitingRoomListener listener) {
            }

            @Override
            public void startGame() {
                stateChangeListener.enteredGame(new GameConfigurerAdapter());
            }
        });
    }

}
