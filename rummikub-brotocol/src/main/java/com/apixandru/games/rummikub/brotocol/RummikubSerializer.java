package com.apixandru.games.rummikub.brotocol;

import com.apixandru.games.rummikub.api.Card;
import com.apixandru.games.rummikub.api.GameOverReason;
import com.apixandru.games.rummikub.brotocol.connect.client.PacketStart;
import com.apixandru.games.rummikub.brotocol.connect.server.PacketPlayerJoined;
import com.apixandru.games.rummikub.brotocol.connect.server.PacketPlayerStart;
import com.apixandru.games.rummikub.brotocol.game.client.PacketEndTurn;
import com.apixandru.games.rummikub.brotocol.game.client.PacketMoveCard;
import com.apixandru.games.rummikub.brotocol.game.client.PacketPlaceCard;
import com.apixandru.games.rummikub.brotocol.game.client.PacketTakeCard;
import com.apixandru.games.rummikub.brotocol.game.server.PacketCardPlaced;
import com.apixandru.games.rummikub.brotocol.game.server.PacketCardRemoved;
import com.apixandru.games.rummikub.brotocol.game.server.PacketGameOver;
import com.apixandru.games.rummikub.brotocol.game.server.PacketNewTurn;
import com.apixandru.games.rummikub.brotocol.game.server.PacketReceiveCard;
import com.apixandru.utils.fieldserializer.FieldSerializer;
import com.apixandru.utils.fieldserializer.FieldSerializerImpl;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
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
        final FieldSerializerImpl serializer = new FieldSerializerImpl();

        serializer.register(Integer.class, (i, output) -> output.writeInt(i == null ? -1 : i));
        serializer.register(Integer.class, input -> {
            final int i = input.readInt();
            return -1 == i ? null : i;
        });

        serializer.register(Card.class, (card, output) -> writeSafeByte(CARDS.indexOf(card), output));
        serializer.register(Card.class, input -> CARDS.get(input.readByte()));

        serializer.register(int.class, RummikubSerializer::writeSafeByte);
        serializer.register(int.class, dataInput -> (int) dataInput.readByte());

        serializer.register(GameOverReason.class, (reason, output) -> writeSafeByte(reason.ordinal(), output));
        serializer.register(GameOverReason.class, input -> GameOverReason.values()[input.readByte()]);

        this.serializer = serializer;

        register(PacketPlaceCard.class);
        register(PacketMoveCard.class);
        register(PacketTakeCard.class);
        register(PacketEndTurn.class);

        register(PacketGameOver.class);
        register(PacketNewTurn.class);
        register(PacketCardPlaced.class);
        register(PacketCardRemoved.class);
        register(PacketReceiveCard.class);

        register(PacketPlayerJoined.class);
        register(PacketStart.class);
        register(PacketPlayerStart.class);
    }

    private static void writeSafeByte(final int intValue, final DataOutput output) throws IOException {
        final byte byteValue = (byte) intValue;
        if (byteValue != intValue) {
            throw new IllegalArgumentException();
        }
        output.writeByte(byteValue);
    }

    private void register(final Class<? extends Packet> packetClass) {
        this.packets.put(packetClass.getAnnotation(Header.class).value().ordinal(), packetClass);
    }

    @Override
    public void serialize(final Packet packet, final DataOutputStream output) throws IOException {
        final int value = packet.getClass().getAnnotation(Header.class).value().ordinal();
        output.writeByte(value);
        serializer.writeFields(packet, output);
        output.flush();
    }

    @Override
    @SuppressWarnings("unchecked")
    public Packet deserialize(final DataInputStream input) throws IOException {
        final int read = input.readByte();
        final Class<Packet> clasz = packets.get(read);
        if (null == clasz) {
            throw new IllegalArgumentException("No handler registered for " + Brotocol.values()[read]);
        }
        return serializer.readFields(clasz, input);
    }

}
