package com.apixandru.rummikub.swing;

import com.apixandru.rummikub.client.RummikubConnector;

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
        final WindowManager windowManager = new WindowManager(connectionData.username);
        final RummikubConnector rummikubConnector = new RummikubConnector(connectionData.socket, windowManager);
        rummikubConnector.connect();

    }

}
