package com.apixandru.games.rummikub.brotocol.util;

import com.apixandru.games.rummikub.api.Card;
import com.apixandru.games.rummikub.brotocol.BroWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 04, 2016
 */
public class AbstractIntWritable {

    protected final BroWriter writer;
    protected final List<Card> cards;

    /**
     * @param writer
     * @param cards
     * @throws IOException
     */
    public AbstractIntWritable(final BroWriter writer, final List<Card> cards) throws IOException {
        this.cards = Collections.unmodifiableList(new ArrayList<>(cards));
        this.writer = writer;
    }

    /**
     * @param header
     * @param card
     * @param ints
     */
    protected final void writeAndFlush(final int header, final Card card, int... ints) {
        write(header, card, ints);
        flush();
    }

    /**
     * @param header
     * @param card
     * @param ints
     */
    protected final void write(final int header, final Card card, int... ints) {
        write(header, this.cards.indexOf(card));
        write(ints);
    }

    /**
     * @param ints
     */
    protected final void write(int... ints) {
        this.writer.write(ints);
    }

    /**
     * @param booleans
     */
    protected final void write(boolean... booleans) {
        for (boolean bool : booleans) {
            write(bool ? 1 : 0);
        }
    }

    /**
     * @param ints
     */
    protected final void writeAndFlush(int... ints) {
        write(ints);
        flush();
    }

    /**
     * @param string
     */
    protected final void write(final String string) {
        this.writer.write(string);
    }

    /**
     *
     */
    protected final void flush() {
        this.writer.flush();
    }

}
