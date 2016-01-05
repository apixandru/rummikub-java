package com.github.apixandru.games.rummikub.server;

import com.github.apixandru.games.rummikub.brotocol.IntWriter;
import com.github.apixandru.games.rummikub.brotocol.SocketIntReader;
import com.github.apixandru.games.rummikub.brotocol.SocketIntWriter;
import com.github.apixandru.games.rummikub.model.Card;
import com.github.apixandru.games.rummikub.model.Cards;
import com.github.apixandru.games.rummikub.model.Constants;
import com.github.apixandru.games.rummikub.model.Player;
import com.github.apixandru.games.rummikub.model.Rummikub;
import com.github.apixandru.games.rummikub.model.RummikubFactory;

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
            final boolean joker = Cards.isJoker(card);
            final int index = i * 2;
            ints[index] = joker ? -1 : card.getColor().ordinal();
            ints[index + 1] = joker ? -1 : card.getRank().ordinal();
        }
        writer.write(ints);
        writer.flush();
    }

}
