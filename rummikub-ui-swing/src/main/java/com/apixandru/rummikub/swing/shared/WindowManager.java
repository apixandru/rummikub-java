package com.apixandru.rummikub.swing.shared;

import com.apixandru.rummikub.client.StateChangeListener;
import com.apixandru.rummikub.client.connector.OnDisconnectListener;
import com.apixandru.rummikub.client.game.GameConfigurer;
import com.apixandru.rummikub.client.waiting.RummikubRoomConfigurer;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import static com.apixandru.rummikub.swing.shared.GameFrame.run;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 09, 2016
 */
public final class WindowManager implements StateChangeListener, OnDisconnectListener {

    private final String username;

    private JFrame currentFrame;

    public WindowManager(final String username) {
        this.username = username;
    }

    @Override
    public void enteredWaitingRoom(final RummikubRoomConfigurer configurer) {
        dismiss();
        final WaitingRoomPanel waitingRoomPanel = new WaitingRoomPanel(configurer);
        configurer.registerListener(waitingRoomPanel);
        currentFrame = WaitingRoomPanel.createAndShowGui(waitingRoomPanel);
    }

    @Override
    public void enteredGame(final GameConfigurer configurer) {
        dismiss();
        currentFrame = run(username, configurer);
    }

    public void dismiss() {
        JFrame frame = this.currentFrame;
        if (null != frame) {
            SwingUtilities.invokeLater(frame::dispose);
        }
    }

    @Override
    public void onDisconnect(String disconnectReason) {
        if (disconnectReason == null || disconnectReason.isEmpty()) {
            disconnectReason = "Disconnected from server";
        }
        JOptionPane.showMessageDialog(null,
                disconnectReason,
                "Connection Lost",
                JOptionPane.ERROR_MESSAGE);
        dismiss();
    }
}
