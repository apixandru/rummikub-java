package com.apixandru.games.rummikub.ui;

import com.apixandru.games.rummikub.client.ConnectionListener;

import javax.swing.*;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 08, 2016
 */
public final class SwingConnectionListener implements ConnectionListener {

    @Override
    public void onDisconnected() {
        JOptionPane.showMessageDialog(null,
                "You have been disconnected from the server. Press OK to exit.",
                "Disconnected",
                JOptionPane.ERROR_MESSAGE);
        System.exit(1);
    }

}
