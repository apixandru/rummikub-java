package com.apixandru.games.rummikub.brotocol;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 13, 2016
 */
public interface PacketWriter {

    /**
     * @param packet
     */
    void writePacket(Packet packet);

}
