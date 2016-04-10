package com.apixandru.games.rummikub.swing;

import com.apixandru.games.rummikub.client.PlayerCallbackAdapter;
import com.apixandru.games.rummikub.client.RummikubConnector;

/**
 * @author Alexandru-Constantin Bledea
 * @since Oct 15, 2015
 */
final class Main {

    public static void main(final String[] args) {
        final ServerData.ConnectionData connectionData = ServerData.getConnectionData();
        if (null == connectionData) {
            return;
        }
        final PlayerUi player = new PlayerUi();
        final WindowManager windowManager = new WindowManager(connectionData.username, player);

        final RummikubConnector<CardSlot> rummikubConnector = new RummikubConnector<>(connectionData.socket, windowManager);
        final PlayerCallbackAdapter<CardSlot> adapter = new PlayerCallbackAdapter<>(player.getAllSlots(), rummikubConnector);

        // fix this!
        windowManager.setAdapter(adapter);

        rummikubConnector.connect();

    }

}
