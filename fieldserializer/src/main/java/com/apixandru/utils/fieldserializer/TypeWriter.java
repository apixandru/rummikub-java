package com.apixandru.utils.fieldserializer;

import java.io.DataOutput;
import java.io.IOException;

/**
 * @param <T> type to write
 * @author Alexandru-Constantin Bledea
 * @since January 14, 2016
 */
public interface TypeWriter<T> {

    /**
     * @param type   the type to serialize
     * @param output the output object
     * @throws IOException
     */
    void write(T type, DataOutput output) throws IOException;

}
