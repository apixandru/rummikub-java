package com.apixandru.utils.fieldserializer;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 15, 2016
 */
public interface FieldSerializer {

    /**
     * @param packet
     * @param output
     * @throws IllegalAccessException
     * @throws IOException
     */
    void writeFields(final Object packet, final DataOutput output) throws IllegalAccessException, IOException;

    /**
     * @param clasz
     * @param input
     * @param <T>
     * @return
     * @throws IOException
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    <T> T readFields(final Class<T> clasz, final DataInput input) throws IOException, IllegalAccessException, InstantiationException;

}
