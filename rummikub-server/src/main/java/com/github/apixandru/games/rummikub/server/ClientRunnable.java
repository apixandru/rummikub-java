package com.github.apixandru.games.rummikub.server;

import com.github.apixandru.games.rummikub.brotocol.IntWriter;
import com.github.apixandru.games.rummikub.brotocol.SocketIntWriter;
import com.github.apixandru.games.rummikub.model.Card;
import com.github.apixandru.games.rummikub.model.Cards;
import com.github.apixandru.games.rummikub.model.Constants;
import com.github.apixandru.games.rummikub.model.PlayerCallback;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

import static com.github.apixandru.games.rummikub.brotocol.Brotocol.AUX_CARDS;
import static com.github.apixandru.games.rummikub.brotocol.Brotocol.SERVER_CARD_PLACED;
import static com.github.apixandru.games.rummikub.brotocol.Brotocol.SERVER_CARD_REMOVED;
import static com.github.apixandru.games.rummikub.brotocol.Brotocol.SERVER_RECEIVED_CARD;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 04, 2016
 */
public class ClientRunnable implements Runnable, PlayerCallback<Integer> {

    private final Socket socket;
    private final List<Card> cards;
    private final IntWriter writer;

    /**
     * @param socket
     * @param cards
     * @throws IOException
     */
    ClientRunnable(final Socket socket, final List<Card> cards) throws IOException {
        this.socket = socket;
        this.cards = cards;
        this.writer = new SocketIntWriter(socket);
    }

    @Override
    public void run() {
        sendCards();
    }

    /**
     *
     */
    private void sendCards() {
        this.writer.write(AUX_CARDS);
        final int[] ints = new int[Constants.NUM_CARDS * 2];
        for (int i = 0, to = this.cards.size(); i < to; i++) {
            final Card card = this.cards.get(i);
            final boolean joker = Cards.isJoker(card);
            final int index = i * 2;
            ints[index] = joker ? -1 : card.getColor().ordinal();
            ints[index + 1] = joker ? -1 : card.getRank().ordinal();
        }
        this.writer.write(ints);
        flush();
    }

    @Override
    public void cardReceived(final Card card, final Integer hint) {
        write(SERVER_RECEIVED_CARD, card, hint == null ? -1 : hint);
    }

    @Override
    public void onCardPlacedOnBoard(final Card card, final int x, final int y) {
        write(SERVER_CARD_PLACED, card, x, y);
    }

    @Override
    public void onCardRemovedFromBoard(final Card card, final int x, final int y) {
        write(SERVER_CARD_REMOVED, card, x, y);
    }

    /**
     * @param header
     * @param card
     * @param ints
     */
    private void write(final int header, final Card card, int... ints) {
        this.writer.write(header, this.cards.indexOf(card));
        this.writer.write(ints);
        flush();
    }

    /**
     *
     */
    private void flush() {
        this.writer.flush();
    }

}
