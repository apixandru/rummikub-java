package com.apixandru.games.rummikub.ui;

import com.apixandru.games.rummikub.client.ConnectionListener;

import javax.swing.*;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 08, 2016
 */
final class SwingConnectionListener implements ConnectionListener {

    private final JFrame frame;

    /**
     * @param frame
     */
    SwingConnectionListener(final JFrame frame) {
        this.frame = frame;
    }

    @Override
    public void onDisconnected() {
        JOptionPane.showMessageDialog(null,
                "You have been disconnected from the server. Press OK to exit.",
                "Disconnected",
                JOptionPane.ERROR_MESSAGE);
        frame.dispose();
    }

}
