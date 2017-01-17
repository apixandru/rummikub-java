package com.apixandru.rummikub.swing;

import com.apixandru.rummikub.brotocol.connect.client.LeaveRequest;
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

        final WindowManager windowManager = new WindowManager(connectionData.username);
        NotifyShutdown shutdownHook = new NotifyShutdown(connectionData, windowManager);
        Runtime.getRuntime().addShutdownHook(shutdownHook);

        final RummikubConnector rummikubConnector = new RummikubConnector(connectionData.socket, windowManager, shutdownHook);
        rummikubConnector.connect();

    }

    private static class NotifyShutdown extends Thread implements ConnectionListener {

        private final ServerData.ConnectionData connectionData;
        private final WindowManager windowManager;

        private boolean send = true;

        private NotifyShutdown(ServerData.ConnectionData connectionData, WindowManager windowManager) {
            this.connectionData = connectionData;
            this.windowManager = windowManager;
        }

        @Override
        public void run() {
            if (send) {
                connectionData.socket.writePacket(new LeaveRequest());
            }
        }

        @Override
        public void onConnectionLost() {
            send = false;
            JOptionPane.showMessageDialog(null,
                    "The connection was lost",
                    "Connection Lost",
                    JOptionPane.ERROR_MESSAGE);
            windowManager.dismiss();
        }

    }

}
