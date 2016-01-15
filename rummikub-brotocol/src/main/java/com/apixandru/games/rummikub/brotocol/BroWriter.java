package com.apixandru.games.rummikub.brotocol;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 04, 2016
 */
public interface BroWriter extends PacketWriter {

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
