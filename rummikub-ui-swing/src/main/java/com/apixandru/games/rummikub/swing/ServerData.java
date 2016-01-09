package com.apixandru.games.rummikub.swing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.Socket;
import java.util.prefs.Preferences;

import static javax.swing.JOptionPane.OK_CANCEL_OPTION;
import static javax.swing.JOptionPane.QUESTION_MESSAGE;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 09, 2016
 */
final class ServerData {

    private static final Logger log = LoggerFactory.getLogger(ServerData.class);

    private static final String KEY_ADDRESS = "address";
    private static final String KEY_USERNAME = "username";

    private static final String OPT_CONNECT = "Connect";
    private static final String OPT_CANCEL = "Cancel";

    private static final String[] OPTIONS = {OPT_CONNECT, OPT_CANCEL};

    private static final int CHOICE_CONNECT = 0;

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

        while (true) {
            final int option = JOptionPane.showOptionDialog(
                    null,
                    createLoginPanel(tfUsername, tfAddress),
                    "Connection Data",
                    OK_CANCEL_OPTION,
                    QUESTION_MESSAGE,
                    null,
                    OPTIONS,
                    OPT_CONNECT);

            if (CHOICE_CONNECT != option) {
                return null;
            }

            final String address = tfAddress.getText();
            final String username = tfUsername.getText();

            prefs.put(KEY_ADDRESS, address);
            prefs.put(KEY_USERNAME, username);

            try {
                return new ConnectionData(new Socket(address, 50122), username);
            } catch (IOException ex) {
                log.debug("Could not connect to " + address);
                JOptionPane.showMessageDialog(null,
                        "Cannot connect to " + address + "!",
                        "Cannot connect",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * @param username
     * @param address
     * @return
     */
    private static JPanel createLoginPanel(final JTextField username, final JTextField address) {
        final JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.add(createLabels(), BorderLayout.WEST);
        panel.add(newPanel(address, username), BorderLayout.CENTER);
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

        final Socket socket;
        final String username;

        /**
         * @param socket
         * @param username
         */
        ConnectionData(final Socket socket, final String username) {
            this.socket = socket;
            this.username = username;
        }

    }

}
