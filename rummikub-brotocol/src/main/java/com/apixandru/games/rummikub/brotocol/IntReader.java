package com.apixandru.games.rummikub.brotocol;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 04, 2016
 */
public interface IntReader extends Closeable {

    /**
     * @return
     * @throws IOException
     */
    int readInt() throws IOException;

}
