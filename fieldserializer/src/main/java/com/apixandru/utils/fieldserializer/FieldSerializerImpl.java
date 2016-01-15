package com.apixandru.utils.fieldserializer;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 15, 2016
 */
public final class FieldSerializerImpl implements FieldSerializer {

    private final Map<Class, TypeWriter> writers = new HashMap<>();
    private final Map<Class, TypeReader> readers = new HashMap<>();

    {
        register(int.class, (i, output) -> output.writeInt(i));
        register(int.class, DataInput::readInt);

        register(boolean.class, (i, output) -> output.writeBoolean(i));
        register(boolean.class, DataInput::readBoolean);

        register(String.class, (string, output) -> output.writeUTF(string));
        register(String.class, DataInput::readUTF);

    }

    /**
     * @param type   the class type
     * @param writer the writer for the class type
     * @param <T>    the type
     */
    public <T> void register(Class<T> type, TypeWriter<T> writer) {
        writers.put(type, writer);
    }

    /**
     * @param type   the class type
     * @param reader the reader for the class type
     * @param <T>    the type
     */
    public <T> void register(Class<T> type, TypeReader<T> reader) {
        readers.put(type, reader);
    }

    /**
     * @param field
     * @param packet
     * @param output
     * @throws IllegalAccessException
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    private void deserialize(final Field field, final Object packet, final DataOutput output) throws IllegalAccessException, IOException {
        final Class<?> type = field.getType();
        if (!writers.containsKey(type)) {
            throw new IOException("No deserializer registered for " + type);
        }
        final TypeWriter typeWriter = writers.get(type);
        typeWriter.writer(field.get(packet), output);
    }

    /**
     * @param field
     * @param object
     * @param input
     * @throws IOException
     * @throws IllegalAccessException
     */
    private void serialize(final Field field, final Object object, final DataInput input) throws IOException, IllegalAccessException {
        final Class<?> type = field.getType();
        if (!readers.containsKey(type)) {
            throw new IOException("No serializer registered for " + type);
        }
        final TypeReader typeReader = readers.get(type);
        field.set(object, typeReader.read(input));
    }

    @Override
    public void writeFields(final Object packet, final DataOutput output) throws IOException {
        try {
            for (final Field field : packet.getClass().getDeclaredFields()) {
                deserialize(field, packet, output);
            }
        } catch (final IllegalAccessException e) {
            throw new IOException("Failed to serialize data", e);
        }
    }

    @Override
    public <T> T readFields(final Class<T> clasz, final DataInput input) throws IOException {
        try {
            final T object = clasz.newInstance();
            for (final Field field : clasz.getDeclaredFields()) {
                serialize(field, object, input);
            }
            return object;
        } catch (final InstantiationException | IllegalAccessException e) {
            throw new IOException("Failed to deserialize data", e);
        }
    }

}
