package com.apixandru.games.rummikub.brotocol;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 04, 2016
 */
public interface BroReader extends Closeable {

    /**
     * @return
     * @throws IOException
     */
    int readInt() throws IOException;

    /**
     * @return
     * @throws IOException
     */
    boolean readBoolean() throws IOException;

    /**
     * @return
     * @throws IOException
     */
    String readString() throws IOException;

}
