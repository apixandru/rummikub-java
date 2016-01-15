package com.apixandru.games.rummikub.brotocol;

import java.io.DataOutput;
import java.io.IOException;

/**
 * @param <T>
 * @author Alexandru-Constantin Bledea
 * @since 1/14/16
 */
interface OutputWriter<T> {

    /**
     * @param type
     * @param output
     * @throws IOException
     */
    void convert(T type, DataOutput output) throws IOException;

}
