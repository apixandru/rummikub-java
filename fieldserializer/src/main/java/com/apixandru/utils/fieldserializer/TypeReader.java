package com.apixandru.utils.fieldserializer;

import java.io.DataInput;
import java.io.IOException;

/**
 * @param <T> type to read
 * @author Alexandru-Constantin Bledea
 * @since January 14, 2016
 */
public interface TypeReader<T> {

    /**
     * @param input the input object
     * @return the read object
     * @throws IOException
     */
    T read(DataInput input) throws IOException;

}
