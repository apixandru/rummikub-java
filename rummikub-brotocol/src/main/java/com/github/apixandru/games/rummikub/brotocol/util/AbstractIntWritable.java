package com.github.apixandru.games.rummikub.brotocol.util;

import com.github.apixandru.games.rummikub.brotocol.IntWriter;
import com.github.apixandru.games.rummikub.model.Card;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 04, 2016
 */
public class AbstractIntWritable {

    protected final IntWriter writer;
    protected final List<Card> cards;

    /**
     * @param writer
     * @param cards
     * @throws IOException
     */
    public AbstractIntWritable(final IntWriter writer, final List<Card> cards) throws IOException {
        this.cards = Collections.unmodifiableList(new ArrayList<>(cards));
        this.writer = writer;
    }

    /**
     * @param header
     * @param card
     * @param ints
     */
    protected final void write(final int header, final Card card, int... ints) {
        write(header, this.cards.indexOf(card));
        write(ints);
        flush();
    }

    /**
     * @param ints
     */
    protected final void write(int... ints) {
        this.writer.write(ints);
    }

    /**
     *
     */
    protected final void flush() {
        this.writer.flush();
    }

}
