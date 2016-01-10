package com.apixandru.games.rummikub.brotocol.util;

import com.apixandru.games.rummikub.api.Card;
import com.apixandru.games.rummikub.brotocol.BroWriter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 04, 2016
 */
public class AbstractIntWritable {

    private final BroWriter writer;
    private final List<Card> cards;

    /**
     * @param writer the writer
     * @param cards  the list of all the cards in the game
     */
    protected AbstractIntWritable(final BroWriter writer, final List<Card> cards) {
        this.cards = Collections.unmodifiableList(new ArrayList<>(cards));
        this.writer = writer;
    }

    /**
     * @param header the opcode
     * @param card   the card
     * @param ints   other values to be written
     */
    protected final void writeAndFlush(final int header, final Card card, int... ints) {
        write(header, card, ints);
        flush();
    }

    /**
     * @param header the opcode
     * @param card   the card
     * @param ints   other values to be written
     */
    protected final void write(final int header, final Card card, int... ints) {
        write(header, this.cards.indexOf(card));
        write(ints);
    }

    /**
     * @param ints values to be written
     */
    protected final void write(int... ints) {
        this.writer.write(ints);
    }

    /**
     * @param booleans values to be written
     */
    protected final void write(boolean... booleans) {
        for (boolean bool : booleans) {
            write(bool ? 1 : 0);
        }
    }

    /**
     * @param ints values to be written
     */
    protected final void writeAndFlush(int... ints) {
        write(ints);
        flush();
    }

    /**
     * @param string the string to be written
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
