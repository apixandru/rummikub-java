package com.apixandru.games.rummikub.brotocol;

import com.apixandru.games.rummikub.api.Card;
import com.apixandru.games.rummikub.brotocol.client.PacketEndTurn;
import com.apixandru.games.rummikub.brotocol.client.PacketMoveCard;
import com.apixandru.games.rummikub.brotocol.client.PacketPlaceCard;
import com.apixandru.games.rummikub.brotocol.client.PacketTakeCard;
import com.apixandru.games.rummikub.brotocol.server.PacketCardPlaced;
import com.apixandru.games.rummikub.brotocol.server.PacketCardRemoved;
import com.apixandru.games.rummikub.brotocol.server.PacketGameOver;
import com.apixandru.games.rummikub.brotocol.server.PacketNewTurn;
import com.apixandru.games.rummikub.brotocol.server.PacketReceiveCard;
import com.apixandru.utils.fieldserializer.FieldSerializer;
import com.apixandru.utils.fieldserializer.FieldSerializerImpl;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.HashMap;
import java.util.Map;

import static com.apixandru.games.rummikub.api.Constants.CARDS;

/**
 * @author Alexandru-Constantin Bledea
 * @since 1/13/16
 */
final class RummikubSerializer implements Serializer {

    private final Map<Integer, Class> packets = new HashMap<>();

    private final FieldSerializer serializer;

    {
        FieldSerializerImpl serialiser = new FieldSerializerImpl();

        serialiser.register(Integer.class, (i, output) -> output.writeInt(i == null ? -1 : i));
        serialiser.register(Integer.class, input -> {
            final int i = input.readInt();
            return -1 == i ? null : i;
        });

        serialiser.register(Card.class, (card, output) -> output.writeInt(CARDS.indexOf(card)));
        serialiser.register(Card.class, input -> CARDS.get(input.readInt()));

        this.serializer = serialiser;

        register(PacketPlaceCard.class);
        register(PacketMoveCard.class);
        register(PacketTakeCard.class);
        register(PacketEndTurn.class);

        register(PacketGameOver.class);
        register(PacketNewTurn.class);
        register(PacketCardPlaced.class);
        register(PacketCardRemoved.class);
        register(PacketReceiveCard.class);
    }

    /**
     * @param packetClass
     */
    private void register(final Class<? extends Packet> packetClass) {
        this.packets.put(packetClass.getAnnotation(Header.class).value(), packetClass);
    }

    @Override
    public void serialize(final Packet packet, final ObjectOutput output) throws IOException {
        try {
            final int value = packet.getClass().getAnnotation(Header.class).value();
            output.write(value);
            serializer.writeFields(packet, output);
            output.flush();
        } catch (IllegalAccessException e) {
            throw new IOException("Failed to serialize packet", e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Packet deserialize(final ObjectInput input) throws IOException {
        final int read = input.read();
        final Class<Packet> aClass = packets.get(read);
        try {
            return serializer.readFields(aClass, input);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IOException("Failed to deserialize packet", e);
        }
    }

}
