package com.apixandru.rummikub.brotocol;

import com.apixandru.rummikub.brotocol2.ConnectionListener;

/**
 * @author Alexandru-Constantin Bledea
 * @since May 04, 2016
 */
public interface ConnectorPacketHandler extends PacketHandler<Packet>, ConnectionListener {

    boolean isReady();

    void addConnectionListener(ConnectionListener connectionListener);

}
