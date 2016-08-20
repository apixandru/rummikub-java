package com.apixandru.rummikub.brotocol;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 13, 2016
 */
public interface PacketWriter {

    void writePacket(Packet packet);

    void close();

}
