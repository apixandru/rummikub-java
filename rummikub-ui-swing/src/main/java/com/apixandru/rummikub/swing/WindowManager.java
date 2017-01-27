package com.apixandru.rummikub.swing;

import com.apixandru.rummikub.api.config.GameConfigurer;
import com.apixandru.rummikub.client.StateChangeListener;
import com.apixandru.rummikub.client.waiting.RummikubRoomConfigurer;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import static com.apixandru.rummikub.swing.GameFrame.run;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 09, 2016
 */
class WindowManager implements StateChangeListener {

    private final String username;

    private JFrame currentFrame;

    WindowManager(final String username) {
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

    void dismiss() {
        JFrame frame = this.currentFrame;
        if (null != frame) {
            SwingUtilities.invokeLater(frame::dispose);
        }
    }

}
