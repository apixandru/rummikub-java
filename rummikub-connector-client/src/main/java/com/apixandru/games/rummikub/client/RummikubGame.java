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
     * @param connector the connector
     * @param socket    the socket
     * @param <H>       the hint type
     * @return the socket player
     * @throws IOException
     */
    static <H> SocketPlayer<H> connect(final RummikubConnector<H> connector, final Socket socket) throws IOException {
        final SocketWrapper wrapper = new SocketWrapper(socket);
        sendPlayerName(connector.playerName, wrapper);
        final List<Card> cards = handleReceiveCardList(wrapper);
        new Thread(new PlayerCallbackAdapter<>(connector, wrapper), "Callback adapter").start();

        return new SocketPlayer<>(connector.playerName, wrapper, connector.hints);
    }

    /**
     * @param playerName the player name
     * @param wrapper    the socket wrapper
     */
    private static void sendPlayerName(final String playerName, final SocketWrapper wrapper) {
        wrapper.write(playerName);
        wrapper.flush();
    }

    /**
     * @param reader the reader
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
     * @param index  the index in the array
     * @param values the array
     * @param <T>    the element type
     * @return the value at the index or null if the index is -1
     */
    private static <T> T orNull(final int index, final T[] values) {
        if (-1 == index) {
            return null;
        }
        return values[index];
    }

}
