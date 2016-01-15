package com.apixandru.games.rummikub.brotocol;

import java.io.Closeable;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 13, 2016
 */
public interface PacketWriter extends Closeable {

    /**
     * @param packet
     */
    void writePacket(Packet packet);

}
