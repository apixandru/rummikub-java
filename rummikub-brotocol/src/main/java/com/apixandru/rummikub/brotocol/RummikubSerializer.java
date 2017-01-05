package com.apixandru.rummikub.brotocol;

import com.apixandru.rummikub.api.game.Card;
import com.apixandru.rummikub.api.game.GameOverReason;
import com.apixandru.rummikub.brotocol.connect.client.PacketLeave;
import com.apixandru.rummikub.brotocol.connect.client.PacketStart;
import com.apixandru.rummikub.brotocol.connect.server.PacketPlayerJoined;
import com.apixandru.rummikub.brotocol.connect.server.PacketPlayerLeft;
import com.apixandru.rummikub.brotocol.connect.server.PacketPlayerStart;
import com.apixandru.rummikub.brotocol.game.client.LoginRequest;
import com.apixandru.rummikub.brotocol.game.client.PacketEndTurn;
import com.apixandru.rummikub.brotocol.game.client.PacketMoveCard;
import com.apixandru.rummikub.brotocol.game.client.PacketPlaceCard;
import com.apixandru.rummikub.brotocol.game.client.PacketTakeCard;
import com.apixandru.rummikub.brotocol.game.server.LoginResponse;
import com.apixandru.rummikub.brotocol.game.server.PacketCardPlaced;
import com.apixandru.rummikub.brotocol.game.server.PacketCardRemoved;
import com.apixandru.rummikub.brotocol.game.server.PacketGameOver;
import com.apixandru.rummikub.brotocol.game.server.PacketNewTurn;
import com.apixandru.rummikub.brotocol.game.server.PacketReceiveCard;
import com.apixandru.rummikub.brotocol.util.FieldSerializer;
import com.apixandru.rummikub.brotocol.util.FieldSerializerImpl;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class deals with serializing / deserializing packets.
 * To make the protocol more lightweight, the identifier for the packet is the index of the class in the list.
 * This is the reason why both the serializer and deserializer must use the exact same list of packets.
 *
 * @author Alexandru-Constantin Bledea
 * @since 1/13/16
 */
final class RummikubSerializer implements Serializer {

    private final List<Class> packets = new ArrayList<>();

    private final FieldSerializer serializer;

    {
        final FieldSerializerImpl serializer = new FieldSerializerImpl();

        serializer.register(int.class, Converters::readInt, Converters::writeInt);
        serializer.register(boolean.class, Converters::readBoolean, Converters::writeBoolean);
        serializer.register(String.class, Converters::readString, Converters::writeString);

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
        register(LoginRequest.class);
        register(LoginResponse.class);
    }

    private void register(final Class<? extends Packet> packetClass) {
        this.packets.add(packetClass);
    }

    @Override
    public void serialize(final Packet packet, final DataOutputStream output) throws IOException {
        Class<? extends Packet> packetClass = packet.getClass();
        validateRegistered(packetClass);
        final int code = packets.indexOf(packetClass);
        output.writeByte(code);
        serializer.writeFields(packet, output);
        output.flush();
    }

    private void validateRegistered(Class<? extends Packet> packet) {
        if (!packets.contains(packet)) {
            throw new IllegalArgumentException("Class " + packet.getName() + " is not registered.");
        }
    }

    /**
     * @param input the data input stream
     * @return the next packet, this method never returns null
     * @throws IOException if an I/O error occurs.
     */
    @Override
    @SuppressWarnings("unchecked")
    public Packet deserialize(final DataInputStream input) throws IOException {
        final int code = input.readByte();
        final Class<Packet> packetClass = packets.get(code);
        return serializer.readFields(packetClass, input);
    }

}
