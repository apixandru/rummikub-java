package com.apixandru.games.rummikub.brotocol;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 13, 2016
 */
public interface PacketReader extends Closeable {

    /**
     * @return packet
     * @throws IOException
     */
    Packet readPacket() throws IOException;

}
