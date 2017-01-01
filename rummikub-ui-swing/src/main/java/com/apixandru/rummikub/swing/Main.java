package com.apixandru.rummikub.swing;

import com.apixandru.rummikub.brotocol.connect.client.PacketLeave;
import com.apixandru.rummikub.brotocol.util.ConnectionListener;
import com.apixandru.rummikub.client.RummikubConnector;

import javax.swing.JOptionPane;

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

        NotifyShutdown runnable = new NotifyShutdown(connectionData);
        Runtime.getRuntime().addShutdownHook(new Thread(runnable));

        final WindowManager windowManager = new WindowManager(connectionData.username);
        ConnectionListener connectionListener = () -> {
            runnable.send = false;
            JOptionPane.showMessageDialog(null,
                    "The connection was lost",
                    "Connection Lost",
                    JOptionPane.ERROR_MESSAGE);
            windowManager.dismiss();
        };

        final RummikubConnector rummikubConnector = new RummikubConnector(connectionData.socket, windowManager, connectionListener);
        rummikubConnector.connect();

    }

    private static class NotifyShutdown implements Runnable {

        private final ServerData.ConnectionData connectionData;
        private boolean send = true;

        private NotifyShutdown(ServerData.ConnectionData connectionData) {
            this.connectionData = connectionData;
        }

        @Override
        public void run() {
            if (send) {
                connectionData.socket.writePacket(new PacketLeave());
            }
        }
    }

}
