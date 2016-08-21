package com.apixandru.rummikub.connection;

import com.apixandru.rummikub.brotocol.Packet;

/**
 * @author Alexandru-Constantin Bledea
 * @since Aug 19, 2016
 */
public interface PacketConnection {

    Packet readPacket() throws ConnectionLostException;

    default void sendPacket(Packet packet) throws ConnectionLostException {
        if (!trySendPacket(packet)) {
            throw new ConnectionLostException();
        }
    }

    boolean trySendPacket(Packet packet);

    void close();

}
