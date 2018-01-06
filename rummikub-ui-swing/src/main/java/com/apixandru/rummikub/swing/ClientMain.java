package com.apixandru.rummikub.swing;

import com.apixandru.rummikub.client.RummikubWebSocketConnector;
import com.apixandru.rummikub.client.connector.OnLoginListener;
import com.apixandru.rummikub.client.connector.RummikubClient;
import com.apixandru.rummikub.swing.shared.WindowManager;
import org.slf4j.Logger;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.JTextComponent;
import javax.websocket.DeploymentException;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.prefs.Preferences;

import static javax.swing.JOptionPane.OK_CANCEL_OPTION;
import static javax.swing.JOptionPane.QUESTION_MESSAGE;
import static org.slf4j.LoggerFactory.getLogger;

final class ClientMain {

    private static final Logger log = getLogger(ClientMain.class);

    private static final String KEY_ADDRESS = "address";
    private static final String KEY_USERNAME = "username";

    private static final String OPT_CONNECT = "Connect";
    private static final String OPT_CANCEL = "Cancel";

    private static final String[] OPTIONS = {OPT_CONNECT, OPT_CANCEL};

    private static final int CHOICE_CONNECT = 0;

    private ClientMain() {
    }

    public static void main(String[] args) {
        RummikubClient client = new RummikubClient();
        client.addOnLoginListener(new OnLoginListener() {
            @Override
            public void onLoggedIn(String playerName) {
                final WindowManager windowManager = new WindowManager(playerName);
                client.addOnDisconnectListener(windowManager);
                final RummikubWebSocketConnector rummikubConnector =
                        new RummikubWebSocketConnector(client, windowManager, client);
                rummikubConnector.connect();
            }

            @Override
            public void onLogInRejected(String reason) {
                showError("Login Rejected: " + reason);
                System.out.println("Login rejected: " + reason);
                tryConnect(client);
            }
        });
        tryConnect(client);
    }

    private static void tryConnect(RummikubClient client) {
        final Preferences prefs = Preferences.userNodeForPackage(ClientMain.class);

        final JTextField tfUsername = newField(prefs, KEY_USERNAME, "rk.user");
        final JTextField tfAddress = newField(prefs, KEY_ADDRESS, "rk.address");

        boolean showDialog = hasText(tfUsername) || hasText(tfAddress);

        if (!client.isConnected()) {
            client.addOnConnectListener(() -> client.login(extractText(tfUsername)));
        } else {
            tfAddress.setEnabled(false);
        }

        while (true) {
            if (showDialog) {

                if (!choseConnect(tfUsername, tfAddress)) {
                    return;
                }
                prefs.put(KEY_ADDRESS, extractText(tfAddress));
                prefs.put(KEY_USERNAME, extractText(tfUsername));
            }
            String address = extractText(tfAddress);
            String username = extractText(tfUsername);
            try {
                if (!client.isConnected()) {
                    client.connect(address);
                } else {
                    client.login(username);
                }
                return;
            } catch (DeploymentException ex) {
                log.debug("Cannot connect to {}", address, ex);
                showError(ex.getMessage());
            } catch (IllegalArgumentException | IOException | URISyntaxException e) {
                showError("Failed to contact server: " + e.getMessage());
                log.error("Failed to contact server", e);
            }
        }
    }

    private static String extractText(JTextComponent textComponent) {
        return textComponent.getText()
                .trim();
    }

    private static boolean hasText(JTextComponent textComponent) {
        return !extractText(textComponent)
                .isEmpty();
    }

    private static void showError(final String message) {
        JOptionPane.showMessageDialog(null,
                message,
                "Cannot connect",
                JOptionPane.ERROR_MESSAGE);
    }

    private static boolean choseConnect(final JTextField username, final JTextField address) {
        return CHOICE_CONNECT == JOptionPane.showOptionDialog(
                null,
                createLoginPanel(username, address),
                "Connection Data",
                OK_CANCEL_OPTION,
                QUESTION_MESSAGE,
                null,
                OPTIONS,
                OPT_CONNECT);
    }

    private static JPanel createLoginPanel(final JTextField username, final JTextField address) {
        final JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.add(createLabels(), BorderLayout.WEST);
        panel.add(newPanel(address, username), BorderLayout.CENTER);
        return panel;
    }

    private static JPanel newPanel(final Component... components) {
        final JPanel controls = new JPanel(new GridLayout(0, 1, 2, 2));
        for (Component component : components) {
            controls.add(component);
        }
        return controls;
    }

    private static JTextField newField(final Preferences prefs, final String key, final String propertyName) {
        String property = System.getProperty(propertyName);
        String preferenceValue = prefs.get(key, "");
        JTextField field = new JTextField(null == property ? preferenceValue : property);
        field.setColumns(17);
        return field;
    }

    private static JPanel createLabels() {
        return newPanel(new JLabel("Server", SwingConstants.RIGHT), new JLabel("Name", SwingConstants.RIGHT));
    }


}
