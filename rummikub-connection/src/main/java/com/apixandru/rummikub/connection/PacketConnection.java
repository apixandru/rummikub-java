package com.apixandru.rummikub.connection;

import com.apixandru.rummikub.brotocol.Packet;

/**
 * @author Alexandru-Constantin Bledea
 * @since Aug 19, 2016
 */
interface PacketConnection {

    Packet readPacket();

    boolean trySendPacket(Packet packet);

    void close();

}
