package com.apixandru.games.rummikub.server;

import com.apixandru.games.rummikub.api.Card;
import com.apixandru.games.rummikub.api.CompoundCallback;
import com.apixandru.games.rummikub.api.Constants;
import com.apixandru.games.rummikub.api.Player;
import com.apixandru.games.rummikub.brotocol.BroWriter;
import com.apixandru.games.rummikub.brotocol.SocketWrapper;
import com.apixandru.games.rummikub.model.Rummikub;
import com.apixandru.games.rummikub.model.RummikubFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.List;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 10, 2016
 */
final class RummikubServer {

    private static final Logger log = LoggerFactory.getLogger(RummikubServer.class);

    /**
     * @param serverSocket the server socket
     */
    RummikubServer(final ServerSocket serverSocket) throws IOException {
        log.debug("Listening on port {}", serverSocket.getLocalPort());

        final Rummikub<Integer> game = RummikubFactory.newInstance();

        final List<Card> cards = game.getCards();

        while (true) {
            log.debug("Waiting for client...");
            final SocketWrapper wrapper = new SocketWrapper(serverSocket.accept());

            final String playerName = wrapper.readString();
            log.debug("Accepted {}.", playerName);
            sendCards(playerName, wrapper, cards);

            log.debug("Registering {}...", playerName);
            final CompoundCallback<Integer> callback = new ClientCallback(playerName, wrapper, cards);

            final Player<Integer> player = game.addPlayer(playerName, callback);
            log.debug("{} registered.", playerName);
            final ClientRunnable runnable = new ClientRunnable(wrapper, cards, player, game);
            new Thread(runnable, playerName).start();
        }

    }

    /**
     * @param playerName the player name
     * @param writer     the writer
     * @param cards      the list of all the cards in the game
     */
    private static void sendCards(final String playerName, final BroWriter writer, final List<Card> cards) {
        log.debug("[{}] Sending cards.", playerName);
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
     * @param constant the enum constant
     * @return the ordinal value of the constant or -1 if null
     */
    private static int ordinal(final Enum<?> constant) {
        return null == constant ? -1 : constant.ordinal();
    }

}
