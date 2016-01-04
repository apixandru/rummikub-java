package com.github.apixandru.games.rummikub.brotocol;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 04, 2016
 */
public interface IntWriter {

    /**
     * @param ints
     */
    void write(int... ints);

    /**
     *
     */
    void flush();

}
