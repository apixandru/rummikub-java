package com.apixandru.games.rummikub.brotocol;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

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
    void serialize(Packet packet, ObjectOutput output) throws IOException;

    /**
     * @param input
     * @return
     * @throws IOException
     */
    Packet deserialize(ObjectInput input) throws IOException;

}
