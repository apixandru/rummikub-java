package com.apixandru.games.rummikub.client;

import com.apixandru.games.rummikub.brotocol.SocketWrapper;
import com.apixandru.games.rummikub.client.waiting.StartGameListenerImpl;
import com.apixandru.rummikub.StateChangeListener;
import com.apixandru.rummikub.waiting.StartGameListener;
import com.apixandru.rummikub.waiting.WaitingRoomListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 02, 2016
 */
public final class RummikubConnector<H> {

    final SocketWrapper socketWrapper;
    final StateChangeListener<H> stateChangeListener;

    private final List<WaitingRoomListener> waitingRoomListeners = new ArrayList<>();

    public RummikubConnector(final SocketWrapper socketWrapper, final StateChangeListener<H> stateChangeListener) {
        this.socketWrapper = socketWrapper;
        this.stateChangeListener = stateChangeListener;
    }

    public void addWaitingRoomListener(final WaitingRoomListener waitingRoomListener) {
        this.waitingRoomListeners.add(waitingRoomListener);
    }

    public StartGameListener newStartGameListener() {
        return new StartGameListenerImpl(this.socketWrapper);
    }

}
