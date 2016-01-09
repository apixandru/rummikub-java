package com.apixandru.games.rummikub.ui;

import javax.swing.*;
import java.awt.*;
import java.util.prefs.Preferences;

import static javax.swing.JOptionPane.OK_CANCEL_OPTION;
import static javax.swing.JOptionPane.QUESTION_MESSAGE;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 09, 2016
 */
final class ServerData {

    private static final String KEY_ADDRESS = "address";
    private static final String KEY_USERNAME = "username";

    private static final String OPT_CONNECT = "Connect";
    private static final String OPT_CANCEL = "Cancel";

    private static final String[] OPTIONS = {"Connect", "Cancel"};

    /**
     *
     */
    private ServerData() {
    }

    /**
     * @return
     */
    public static ConnectionData getConnectionData() {
        final Preferences prefs = Preferences.userNodeForPackage(ServerData.class);

        final JTextField tfUsername = newField(prefs, KEY_USERNAME);
        final JTextField tfAddress = newField(prefs, KEY_ADDRESS);

        final int option = JOptionPane.showOptionDialog(
                null,
                createLoginPanel(tfUsername, tfAddress),
                "Rummikub Connector",
                OK_CANCEL_OPTION,
                QUESTION_MESSAGE,
                null,
                OPTIONS,
                OPT_CONNECT);

        if (option == JOptionPane.CANCEL_OPTION) {
            return null;
        }

        final String username = tfUsername.getText();
        final String address = tfAddress.getText();

        prefs.put(KEY_ADDRESS, address);
        prefs.put(KEY_USERNAME, username);

        return new ConnectionData(address, username);
    }

    /**
     * @param tfUsername
     * @param tfAddress
     * @return
     */
    private static JPanel createLoginPanel(final JTextField tfUsername, final JTextField tfAddress) {
        final JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.add(createLabels(), BorderLayout.WEST);
        panel.add(newPanel(tfUsername, tfAddress), BorderLayout.CENTER);
        return panel;
    }

    /**
     * @param components
     * @return
     */
    private static JPanel newPanel(final Component... components) {
        final JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
        for (Component component : components) {
            controls.add(component);
        }
        return controls;
    }

    /**
     * @param prefs
     * @param key
     * @return
     */
    private static JTextField newField(final Preferences prefs, final String key) {
        return new JTextField(prefs.get(key, ""));
    }

    /**
     * @return
     */
    private static JPanel createLabels() {
        return newPanel(new JLabel("Server", SwingConstants.RIGHT), new JLabel("Name", SwingConstants.RIGHT));
    }

    /**
     *
     */
    static final class ConnectionData {

        final String ipAddress;
        final String username;

        /**
         * @param ipAddress
         * @param username
         */
        ConnectionData(final String ipAddress, final String username) {
            this.ipAddress = ipAddress;
            this.username = username;
        }

    }

}
