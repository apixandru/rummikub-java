package com.apixandru.games.rummikub.client;

import com.apixandru.games.rummikub.api.Card;
import com.apixandru.games.rummikub.api.Color;
import com.apixandru.games.rummikub.api.Constants;
import com.apixandru.games.rummikub.api.Player;
import com.apixandru.games.rummikub.api.PlayerCallback;
import com.apixandru.games.rummikub.api.Rank;
import com.apixandru.games.rummikub.brotocol.Brotocol;
import com.apixandru.games.rummikub.brotocol.IntReader;
import com.apixandru.games.rummikub.brotocol.SocketIntReader;
import com.apixandru.games.rummikub.brotocol.SocketIntWriter;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 04, 2016
 */
public class RummikubGame {

    /**
     * @param ip
     * @param callback
     * @param hints
     * @param <H>
     * @return
     * @throws IOException
     */
    public static <H> Player<H> connect(final String ip, final PlayerCallback<H> callback, final List<H> hints) throws IOException {
        final Socket socket = new Socket(ip, 50122);
        final SocketIntReader reader = new SocketIntReader(socket);
        final List<Card> cards = handleReceiveCardList(reader);
        final SocketIntWriter writer = new SocketIntWriter(socket);

        new Thread(new PlayerCallbackAdapter<>(reader, callback, cards, hints)).start();
        return new SocketPlayer<>(writer, cards, hints);
    }


    /**
     * @param reader
     * @throws IOException
     */
    private static List<Card> handleReceiveCardList(final IntReader reader) throws IOException {
        final int i1 = reader.readInt();
        if (Brotocol.AUX_CARDS != i1) {
            throw new IllegalArgumentException("Unexpected header: " + i1);
        }
        final List<Card> cards = new ArrayList<>();

        final Color[] colorValues = Color.values();
        final Rank[] rankValues = Rank.values();

        for (int i = 0; i < Constants.NUM_CARDS; i++) {
            cards.add(new Card(orNull(reader.readInt(), colorValues), orNull(reader.readInt(), rankValues)));
        }
        return cards;
    }

    /**
     * @param index
     * @param values
     * @param <T>
     * @return
     */
    private static <T> T orNull(final int index, T... values) {
        if (-1 == index) {
            return null;
        }
        return values[index];
    }

}
