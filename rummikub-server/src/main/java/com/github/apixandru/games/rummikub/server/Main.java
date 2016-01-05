package com.github.apixandru.games.rummikub.server;

import com.github.apixandru.games.rummikub.brotocol.IntWriter;
import com.github.apixandru.games.rummikub.brotocol.SocketIntReader;
import com.github.apixandru.games.rummikub.brotocol.SocketIntWriter;
import com.github.apixandru.games.rummikub.api.Card;
import com.github.apixandru.games.rummikub.api.Constants;
import com.github.apixandru.games.rummikub.api.Player;
import com.github.apixandru.games.rummikub.api.Rummikub;
import com.github.apixandru.games.rummikub.api.RummikubFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import static com.github.apixandru.games.rummikub.brotocol.Brotocol.AUX_CARDS;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 04, 2016
 */
public class Main {

    public static void main(String[] args) throws IOException {

        final ServerSocket serverSocket = new ServerSocket(50122);

        final Rummikub game = RummikubFactory.newInstance();

        final List<Card> cards = game.getCards();

        while (true) {
            final Socket socket = serverSocket.accept();
            final SocketIntWriter writer = new SocketIntWriter(socket);
            sendCards(writer, cards);
            final SocketIntReader reader = new SocketIntReader(socket);
            final Player<Integer> player = game.addPlayer("Player", new ClientCallback(writer, cards));
            final ClientRunnable runnable = new ClientRunnable(reader, cards, player);
            new Thread(runnable).start();
        }

    }

    /**
     *
     */
    private static void sendCards(final IntWriter writer, final List<Card> cards) {
        writer.write(AUX_CARDS);
        final int[] ints = new int[Constants.NUM_CARDS * 2];
        for (int i = 0, to = cards.size(); i < to; i++) {
            final Card card = cards.get(i);
            final int index = i * 2;
            ints[index] = ordinal(card.getColor());
            ints[index + 1] = ordinal(card.getRank());
        }
        writer.write(ints);
        writer.flush();
    }

    /**
     * @param constant
     * @param <E>
     * @return
     */
    private static <E extends Enum<E>> int ordinal(final E constant) {
        return null == constant ? -1 : constant.ordinal();
    }

}
