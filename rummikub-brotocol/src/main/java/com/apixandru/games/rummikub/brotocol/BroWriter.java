package com.apixandru.games.rummikub.brotocol;

import java.io.Closeable;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 04, 2016
 */
public interface BroWriter extends Closeable {

    /**
     * @param ints the ints to be written
     */
    void write(int... ints);

    /**
     * @param string the string to be written
     */
    void write(String string);

    /**
     *
     */
    void flush();

}
