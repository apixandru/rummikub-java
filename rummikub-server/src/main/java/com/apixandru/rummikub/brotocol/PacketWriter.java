package com.apixandru.rummikub.brotocol;

import java.io.Closeable;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 13, 2016
 */
public interface PacketWriter extends Closeable {

    void writePacket(Packet packet);

}
