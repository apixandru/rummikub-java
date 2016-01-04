package com.github.apixandru.games.rummikub.client;

import com.github.apixandru.games.rummikub.brotocol.SocketIntReader;
import com.github.apixandru.games.rummikub.model.PlayerCallback;

import java.io.IOException;
import java.net.Socket;
import java.util.List;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 04, 2016
 */
public class RummikubGame {

    public static <H> void connect(final PlayerCallback<H> callback, final List<H> hints) throws IOException {
        final Socket localhost = new Socket("localhost", 50122);
        new Thread(new PlayerCallbackAdapter<>(new SocketIntReader(localhost), callback, hints)).start();
    }

}
