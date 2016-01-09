package com.apixandru.games.rummikub.client;

import com.apixandru.games.rummikub.api.Card;
import com.apixandru.games.rummikub.api.Color;
import com.apixandru.games.rummikub.api.Constants;
import com.apixandru.games.rummikub.api.Rank;
import com.apixandru.games.rummikub.brotocol.BroReader;
import com.apixandru.games.rummikub.brotocol.SocketWrapper;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 04, 2016
 */
final class RummikubGame {

    /**
     * @param <H>
     * @param connector
     * @param ip
     * @return
     * @throws IOException
     */
    static <H> SocketPlayer<H> connect(final RummikubConnector<H> connector, final String ip) throws IOException {
        final Socket socket = new Socket(ip, 50122);
        final SocketWrapper wrapper = new SocketWrapper(socket);
        sendPlayerName(connector.playerName, wrapper);
        final List<Card> cards = handleReceiveCardList(wrapper);
        new Thread(new PlayerCallbackAdapter<>(connector, wrapper, cards), "Callback adapter").start();

        return new SocketPlayer<>(connector.playerName, wrapper, cards, connector.hints);
    }

    /**
     * @param playerName
     * @param wrapper
     * @param <H>
     */
    private static <H> void sendPlayerName(final String playerName, final SocketWrapper wrapper) {
        wrapper.write(playerName);
        wrapper.flush();
    }


    /**
     * @param reader
     * @throws IOException
     */
    private static List<Card> handleReceiveCardList(final BroReader reader) throws IOException {
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
    private static <T> T orNull(final int index, final T[] values) {
        if (-1 == index) {
            return null;
        }
        return values[index];
    }

}
