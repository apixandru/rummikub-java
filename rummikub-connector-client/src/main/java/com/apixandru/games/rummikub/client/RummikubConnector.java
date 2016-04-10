package com.apixandru.games.rummikub.client;

import com.apixandru.games.rummikub.brotocol.SocketWrapper;
import com.apixandru.rummikub.StateChangeListener;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 02, 2016
 */
public final class RummikubConnector<H> {

    final SocketWrapper socketWrapper;
    final StateChangeListener<H> stateChangeListener;

    public RummikubConnector(final SocketWrapper socketWrapper, final StateChangeListener<H> stateChangeListener) {
        this.socketWrapper = socketWrapper;
        this.stateChangeListener = stateChangeListener;
    }

}
