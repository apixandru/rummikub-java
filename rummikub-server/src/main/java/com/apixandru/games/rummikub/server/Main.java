package com.apixandru.games.rummikub.server;

import com.apixandru.games.rummikub.api.Card;
import com.apixandru.games.rummikub.api.Constants;
import com.apixandru.games.rummikub.api.Player;
import com.apixandru.games.rummikub.brotocol.IntWriter;
import com.apixandru.games.rummikub.brotocol.SocketIntReader;
import com.apixandru.games.rummikub.brotocol.SocketIntWriter;
import com.apixandru.games.rummikub.model.Rummikub;
import com.apixandru.games.rummikub.model.RummikubFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

import static com.apixandru.games.rummikub.brotocol.Brotocol.AUX_CARDS;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 04, 2016
 */
public class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws IOException {

        final ServerSocket serverSocket = new ServerSocket(50122);

        log.debug("Listening on port {}", 50122);

        final Rummikub game = RummikubFactory.newInstance();

        final List<Card> cards = game.getCards();

        int playerId = 0;
        while (true) {
            log.debug("Waiting for client...");
            final Socket socket = serverSocket.accept();
            log.debug("Accepted client.");
            final SocketIntWriter writer = new SocketIntWriter(socket);
            sendCards(writer, cards);
            final SocketIntReader reader = new SocketIntReader(socket);
            log.debug("Registering player...");
            final Player<Integer> player = game.addPlayer("Player", new ClientCallback(writer, cards));
            log.debug("Player registered.");
            final ClientRunnable runnable = new ClientRunnable(reader, cards, player);
            new Thread(runnable, "Player " + playerId++).start();
        }

    }

    /**
     *
     */
    private static void sendCards(final IntWriter writer, final List<Card> cards) {
        log.debug("Sending cards to client.");
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
