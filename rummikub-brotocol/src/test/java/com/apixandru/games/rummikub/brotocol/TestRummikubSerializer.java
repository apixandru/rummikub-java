package com.apixandru.games.rummikub.brotocol;

import com.apixandru.games.rummikub.api.Constants;
import com.apixandru.games.rummikub.brotocol.client.PacketPlaceCard;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

/**
 * @author Alexandru-Constantin Bledea
 * @since Jan 15, 2015
 */
public final class TestRummikubSerializer {

    /**
     *
     */
    @Test
    public void testSerializeDeserialize() throws IOException {
        final RummikubSerializer rummikubSerializer = new RummikubSerializer();

        final PacketPlaceCard packet = new PacketPlaceCard();
        packet.card = Constants.CARDS.get(0);
        packet.x = 3;
        packet.y = 4;

        final byte[] bytes;
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
             ObjectOutputStream daos = new ObjectOutputStream(baos)) {
            rummikubSerializer.serialize(packet, daos);
            bytes = baos.toByteArray();
        }

        final PacketPlaceCard deserialized;
        try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
             ObjectInputStream dais = new ObjectInputStream(bais)) {
            Packet packet2 = rummikubSerializer.deserialize(dais);
            assertNotNull(packet2);
            deserialized = (PacketPlaceCard) packet2;
        }

        assertSame(packet.card, deserialized.card);
        assertSame(packet.x, deserialized.x);
        assertSame(packet.y, deserialized.y);
    }

}
