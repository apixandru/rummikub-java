package com.apixandru.rummikub.fieldserializer;

import java.io.DataOutput;
import java.io.IOException;

/**
 * @param <T> type to write
 * @author Alexandru-Constantin Bledea
 * @since January 14, 2016
 */
public interface TypeWriter<T> {

    void write(T data, DataOutput output) throws IOException;

}
