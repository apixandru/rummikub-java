package com.apixandru.games.rummikub.client;

import com.apixandru.games.rummikub.brotocol.SocketWrapper;
import com.apixandru.games.rummikub.brotocol.connect.StartGameListener;
import com.apixandru.games.rummikub.brotocol.connect.WaitingRoomListener;
import com.apixandru.games.rummikub.client.waiting.StartGameListenerImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 02, 2016
 */
public final class RummikubConnector {

    private final SocketWrapper socketWrapper;

    private final List<WaitingRoomListener> waitingRoomListeners = new ArrayList<>();

    public RummikubConnector(final SocketWrapper socketWrapper) {
        this.socketWrapper = socketWrapper;
    }

    public void addWaitingRoomListener(final WaitingRoomListener waitingRoomListener) {
        this.waitingRoomListeners.add(waitingRoomListener);
    }

    public StartGameListener newStartGameListener() {
        return new StartGameListenerImpl(this.socketWrapper);
    }

}
