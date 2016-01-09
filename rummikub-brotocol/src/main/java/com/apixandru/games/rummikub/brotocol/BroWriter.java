package com.apixandru.games.rummikub.brotocol;

import java.io.Closeable;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 04, 2016
 */
public interface BroWriter extends Closeable {

    /**
     * @param ints
     */
    void write(int... ints);

    /**
     * @param string
     */
    void write(String string);

    /**
     *
     */
    void flush();

}
