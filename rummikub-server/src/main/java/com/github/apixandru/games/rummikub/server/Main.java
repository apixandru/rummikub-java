package com.github.apixandru.games.rummikub.server;

import com.github.apixandru.games.rummikub.model.Card;
import com.github.apixandru.games.rummikub.model.Rummikub;
import com.github.apixandru.games.rummikub.model.RummikubFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 04, 2016
 */
public class Main {

    public static void main(String[] args) throws IOException {

        final ServerSocket serverSocket = new ServerSocket(50122);

        final Rummikub game = RummikubFactory.newInstance();

        final List<Card> cards = game.getCards();

        final List<ClientRunnable> sockets = new ArrayList<>();

        while (true) {
            final ClientRunnable runnable = new ClientRunnable(serverSocket.accept(), cards);
            sockets.add(runnable);
            new Thread(runnable).start();
        }

    }

}
