package com.apixandru.games.rummikub.brotocol;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Alexandru-Constantin Bledea
 * @since 1/13/16
 */
final class RummikubSerializer implements Serializer {

    private final Map<Integer, Packet> packets = new HashMap<>();

    @Override
    public void serialize(final Packet packet, final ObjectOutput output) throws IOException {
        // this is really not that safe and probably has a large overhead
        output.writeObject(packet);
        output.flush();
    }

    @Override
    public Packet deserialize(final ObjectInput input) throws IOException {
        // this is really not that safe and probably has a large overhead
        try {
            return (Packet) input.readObject();
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("Cannot deserialize packet", e);
        }
    }


}
