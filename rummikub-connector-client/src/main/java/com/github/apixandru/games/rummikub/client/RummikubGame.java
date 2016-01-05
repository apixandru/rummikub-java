package com.github.apixandru.games.rummikub.client;

import com.github.apixandru.games.rummikub.brotocol.IntReader;
import com.github.apixandru.games.rummikub.brotocol.SocketIntReader;
import com.github.apixandru.games.rummikub.brotocol.SocketIntWriter;
import com.github.apixandru.games.rummikub.model.Card;
import com.github.apixandru.games.rummikub.model.Color;
import com.github.apixandru.games.rummikub.model.Constants;
import com.github.apixandru.games.rummikub.model.Player;
import com.github.apixandru.games.rummikub.model.PlayerCallback;
import com.github.apixandru.games.rummikub.model.Rank;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 04, 2016
 */
public class RummikubGame {

    public static <H> Player<H> connect(final PlayerCallback<H> callback, final List<H> hints) throws IOException {
        final Socket socket = new Socket("localhost", 50122);
        final SocketIntReader reader = new SocketIntReader(socket);
        final SocketIntWriter writer = new SocketIntWriter(socket);

        final List<Card> cards = handleReceiveCardList(reader);

        new Thread(new PlayerCallbackAdapter<>(reader, callback, hints)).start();
        return new SocketPlayer<>(writer, Collections.emptyList(), hints);
    }


    /**
     * @param reader
     * @throws IOException
     */
    private static List<Card> handleReceiveCardList(final IntReader reader) throws IOException {

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
