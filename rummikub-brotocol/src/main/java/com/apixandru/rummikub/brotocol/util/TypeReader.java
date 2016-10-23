package com.apixandru.rummikub.brotocol.util;

import java.io.DataInput;
import java.io.IOException;

/**
 * @param <T> type to read
 * @author Alexandru-Constantin Bledea
 * @since January 14, 2016
 */
public interface TypeReader<T> {

    T read(DataInput input) throws IOException;

}
