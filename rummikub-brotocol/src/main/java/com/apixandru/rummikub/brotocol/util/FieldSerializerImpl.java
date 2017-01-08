package com.apixandru.rummikub.brotocol.util;

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

    public <T> void register(Class<T> type, TypeReader<T> reader, TypeWriter<T> writer) {
        writers.put(type, writer);
        readers.put(type, reader);
    }

    @SuppressWarnings("unchecked")
    private void write(final Field field, final Object object, final DataOutput output) throws IllegalAccessException, IOException {
        final Class<?> type = field.getType();
        if (!writers.containsKey(type)) {
            throw new IOException("No deserializer registered for " + type);
        }
        final TypeWriter typeWriter = writers.get(type);
        typeWriter.write(field.get(object), output);
    }

    private void read(final Field field, final Object object, final DataInput input) throws IOException, IllegalAccessException {
        final Class<?> type = field.getType();
        if (!readers.containsKey(type)) {
            throw new IOException("No serializer registered for " + type);
        }
        final TypeReader typeReader = readers.get(type);
        field.set(object, typeReader.read(input));
    }

    @Override
    public void writeFields(final Object object, final DataOutput output) throws IOException {
        try {
            for (final Field field : object.getClass().getDeclaredFields()) {
                write(field, object, output);
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
                read(field, object, input);
            }
            return object;
        } catch (final InstantiationException | IllegalAccessException e) {
            throw new IOException("Failed to deserialize data", e);
        }
    }

}