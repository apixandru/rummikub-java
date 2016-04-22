package com.apixandru.rummikub.brotocol;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * @author Alexandru-Constantin Bledea
 * @since 1/13/16
 */
interface Serializer {

    void serialize(Packet packet, DataOutputStream output) throws IOException;

    Packet deserialize(DataInputStream input) throws IOException;

}
