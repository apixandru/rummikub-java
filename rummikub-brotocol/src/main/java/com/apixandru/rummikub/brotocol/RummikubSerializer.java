package com.apixandru.rummikub.brotocol;

import com.apixandru.rummikub.api.Card;
import com.apixandru.rummikub.api.GameOverReason;
import com.apixandru.rummikub.brotocol.connect.client.PacketStart;
import com.apixandru.rummikub.brotocol.connect.server.PacketPlayerJoined;
import com.apixandru.rummikub.brotocol.connect.server.PacketPlayerStart;
import com.apixandru.rummikub.brotocol.game.client.PacketEndTurn;
import com.apixandru.rummikub.brotocol.game.client.PacketMoveCard;
import com.apixandru.rummikub.brotocol.game.client.PacketPlaceCard;
import com.apixandru.rummikub.brotocol.game.client.PacketTakeCard;
import com.apixandru.rummikub.brotocol.game.server.PacketCardPlaced;
import com.apixandru.rummikub.brotocol.game.server.PacketCardRemoved;
import com.apixandru.rummikub.brotocol.game.server.PacketGameOver;
import com.apixandru.rummikub.brotocol.game.server.PacketNewTurn;
import com.apixandru.rummikub.brotocol.game.server.PacketReceiveCard;
import com.apixandru.utils.fieldserializer.FieldSerializer;
import com.apixandru.utils.fieldserializer.FieldSerializerImpl;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.apixandru.rummikub.api.Constants.CARDS;

/**
 * @author Alexandru-Constantin Bledea
 * @since 1/13/16
 */
final class RummikubSerializer implements Serializer {

    private final Map<Integer, Class> packets = new HashMap<>();

    private final FieldSerializer serializer;

    {
        final FieldSerializerImpl serializer = new FieldSerializerImpl();

        serializer.register(Integer.class, RummikubSerializer::writePositiveIntegerAsByte);
        serializer.register(Integer.class, RummikubSerializer::readInteger);

        serializer.register(Card.class, (card, output) -> writeSafeByte(CARDS.indexOf(card), output));
        serializer.register(Card.class, input -> CARDS.get(input.readByte()));

        serializer.register(int.class, RummikubSerializer::writeSafeByte);
        serializer.register(int.class, RummikubSerializer::readByte);

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

    private static Integer readInteger(final DataInput input) throws IOException {
        final int i = readByte(input);
        return -1 == i ? null : i;
    }

    private static void writePositiveIntegerAsByte(final Integer integer, final DataOutput output) throws IOException {
        int value;
        if (null == integer) {
            value = -1;
        } else if (integer < 0) {
            throw new IllegalArgumentException();
        } else {
            value = integer;
        }
        writeSafeByte(value, output);
    }

    private static void writeSafeByte(final int intValue, final DataOutput output) throws IOException {
        final byte byteValue = (byte) intValue;
        if (byteValue != intValue) {
            throw new IllegalArgumentException();
        }
        output.writeByte(byteValue);
    }

    private static int readByte(final DataInput input) throws IOException {
        return input.readByte();
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
