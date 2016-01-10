package com.apixandru.games.rummikub.brotocol;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 04, 2016
 */
public interface BroReader extends Closeable {

    /**
     * @return the next value as an integer
     * @throws IOException
     */
    int readInt() throws IOException;

    /**
     * @return the next value as a boolean
     * @throws IOException
     */
    boolean readBoolean() throws IOException;

    /**
     * @return the next value as a string
     * @throws IOException
     */
    String readString() throws IOException;

}
