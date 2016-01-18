package com.apixandru.games.rummikub.brotocol;

import com.apixandru.games.rummikub.api.Constants;
import com.apixandru.games.rummikub.brotocol.client.PacketPlaceCard;
import com.apixandru.games.rummikub.brotocol.server.PacketGameOver;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

/**
 * @author Alexandru-Constantin Bledea
 * @since Jan 15, 2015
 */
public final class TestRummikubSerializer {

    private final RummikubSerializer rummikubSerializer = new RummikubSerializer();

    /**
     *
     */
    @Test
    public void testSerializeDeserialize() throws IOException {
        final PacketPlaceCard original = new PacketPlaceCard();
        original.card = Constants.CARDS.get(0);
        original.x = 3;
        original.y = 4;

        final PacketPlaceCard deserialized = transport(original);
        assertSame(original.card, deserialized.card);
        assertEquals(original.x, deserialized.x);
        assertEquals(original.y, deserialized.y);
    }

    /**
     *
     */
    @Test
    public void testPacketGameOver() throws IOException {
        final PacketGameOver original = new PacketGameOver();
        original.player = "Shaggy";
        original.quit = false;
        original.me = true;

        final PacketGameOver deserialized = transport(original);
        assertEquals(original.player, deserialized.player);
        assertEquals(original.quit, deserialized.quit);
        assertEquals(original.me, deserialized.me);
    }

    /**
     * @param packet
     * @param <P>
     * @return
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    private <P extends Packet> P transport(final P packet) throws IOException {
        final byte[] bytes;
        try (final ByteArrayOutputStream baos = new ByteArrayOutputStream();
             final DataOutputStream daos = new DataOutputStream(baos)) {
            rummikubSerializer.serialize(packet, daos);
            bytes = baos.toByteArray();
        }

        try (final ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
             final DataInputStream dais = new DataInputStream(bais)) {
            final Packet deserialized = rummikubSerializer.deserialize(dais);
            assertNotNull(deserialized);
            return (P) deserialized;
        }
    }

}
