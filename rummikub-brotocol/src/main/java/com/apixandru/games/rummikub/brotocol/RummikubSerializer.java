package com.apixandru.games.rummikub.brotocol;

import com.apixandru.games.rummikub.api.Card;
import com.apixandru.games.rummikub.brotocol.client.PacketEndTurn;
import com.apixandru.games.rummikub.brotocol.client.PacketMoveCard;
import com.apixandru.games.rummikub.brotocol.client.PacketPlaceCard;
import com.apixandru.games.rummikub.brotocol.client.PacketTakeCard;

import java.io.DataInput;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static com.apixandru.games.rummikub.api.Constants.CARDS;

/**
 * @author Alexandru-Constantin Bledea
 * @since 1/13/16
 */
final class RummikubSerializer implements Serializer {

    private final Map<Integer, Class> packets = new HashMap<>();
    private final Map<Class, OutputWriter> writers = new HashMap<>();
    private final Map<Class, InputReader> readers = new HashMap<>();

    {
        register(int.class, (i, output) -> output.write(i));
        register(int.class, DataInput::readInt);

        register(Integer.class, (i, output) -> output.write(i == null ? -1 : i));
        register(Integer.class, input -> {
            final int i = input.readInt();
            return -1 == i ? null : i;
        });

        register(Card.class, (card, output) -> output.write(CARDS.indexOf(card)));
        register(Card.class, input -> CARDS.get(input.readInt()));

        register(PacketPlaceCard.class);
        register(PacketMoveCard.class);
        register(PacketTakeCard.class);
        register(PacketEndTurn.class);
    }

    /**
     * @param packetClass
     */
    private void register(final Class<? extends Packet> packetClass) {
        this.packets.put(packetClass.getAnnotation(Header.class).value(), packetClass);
    }

    /**
     * @param type
     * @param writer
     * @param <T>
     */
    private <T> void register(Class<T> type, OutputWriter<T> writer) {
        writers.put(type, writer);
    }

    /**
     * @param type
     * @param reader
     * @param <T>
     */
    private <T> void register(Class<T> type, InputReader<T> reader) {
        readers.put(type, reader);
    }

    @Override
    public void serialize(final Packet packet, final ObjectOutput output) throws IOException {
        try {
            final int value = packet.getClass().getAnnotation(Header.class).value();
            output.write(value);
            for (final Field field : packet.getClass().getDeclaredFields()) {
                writers.get(field.getType()).convert(field.get(packet), output);
            }
            output.flush();
        } catch (IllegalAccessException e) {
            throw new IOException("Failed to serialize packet", e);
        }
    }

    @Override
    public Packet deserialize(final ObjectInput input) throws IOException {
        final int read = input.read();
        final Class aClass = packets.get(read);
        try {
            final Packet packet = (Packet) aClass.newInstance();

            for (final Field field : packet.getClass().getDeclaredFields()) {
                field.set(packet, readers.get(field.getType()).read(input));
            }
            return packet;
        } catch (InstantiationException | IllegalAccessException e) {
            throw new IOException("Failed to deserialize packet", e);
        }
    }

}
