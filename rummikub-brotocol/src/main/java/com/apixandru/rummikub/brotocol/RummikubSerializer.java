package com.apixandru.rummikub.brotocol;

import com.apixandru.rummikub.api.game.Card;
import com.apixandru.rummikub.api.game.GameOverReason;
import com.apixandru.rummikub.brotocol.connect.client.PacketLeave;
import com.apixandru.rummikub.brotocol.connect.client.PacketStart;
import com.apixandru.rummikub.brotocol.connect.server.PacketPlayerJoined;
import com.apixandru.rummikub.brotocol.connect.server.PacketPlayerLeft;
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
import com.apixandru.rummikub.fieldserializer.FieldSerializer;
import com.apixandru.rummikub.fieldserializer.FieldSerializerImpl;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Alexandru-Constantin Bledea
 * @since 1/13/16
 */
public final class RummikubSerializer implements Serializer {

    private final Map<Integer, Class> packets = new HashMap<>();

    private final FieldSerializer serializer;

    {
        final FieldSerializerImpl serializer = new FieldSerializerImpl();

        serializer.register(Integer.class, Converters::readInteger, Converters::writeInteger);
        serializer.register(Card.class, Converters::readCard, Converters::writeCard);
        serializer.register(int.class, Converters::readByte, Converters::writeSafeByte);
        serializer.register(GameOverReason.class, Converters::readGameOverReason, Converters::writeGameOverReason);

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
        register(PacketLeave.class);
        register(PacketPlayerLeft.class);
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
