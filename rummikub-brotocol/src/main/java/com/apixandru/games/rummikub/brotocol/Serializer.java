package com.apixandru.games.rummikub.brotocol;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author Alexandru-Constantin Bledea
 * @since 1/13/16
 */
public interface Serializer {

    /**
     * @param packet
     * @param output
     * @throws IOException
     */
    void serialize(Packet packet, DataOutputStream output) throws IOException;

    /**
     * @param input
     * @return
     * @throws IOException
     */
    Packet deserialize(DataInputStream input) throws IOException;

}
