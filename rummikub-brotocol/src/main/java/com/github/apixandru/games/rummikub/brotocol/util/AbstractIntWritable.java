package com.github.apixandru.games.rummikub.brotocol.util;

import com.github.apixandru.games.rummikub.brotocol.IntWriter;
import com.github.apixandru.games.rummikub.brotocol.SocketIntWriter;
import com.github.apixandru.games.rummikub.model.Card;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 04, 2016
 */
public class AbstractIntWritable {

    protected final IntWriter writer;
    protected final Socket socket;
    protected final List<Card> cards;

    /**
     * @param socket
     * @param cards
     * @throws IOException
     */
    public AbstractIntWritable(Socket socket, final List<Card> cards) throws IOException {
        this.socket = socket;
        this.cards = Collections.unmodifiableList(new ArrayList<>(cards));
        this.writer = new SocketIntWriter(socket);
    }

    /**
     * @param header
     * @param card
     * @param ints
     */
    protected final void write(final int header, final Card card, int... ints) {
        this.writer.write(header, this.cards.indexOf(card));
        this.writer.write(ints);
        flush();
    }

    /**
     *
     */
    protected final void flush() {
        this.writer.flush();
    }

}
