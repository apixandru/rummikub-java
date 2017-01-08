package com.apixandru.rummikub.swing;

import com.apixandru.rummikub.api.config.GameConfigurer;
import com.apixandru.rummikub.api.config.RummikubRoomConfigurer;
import com.apixandru.rummikub.client.StateChangeListener;

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
        final WaitingRoom waitingRoom = new WaitingRoom(configurer);
        configurer.registerListener(waitingRoom);
        currentFrame = WaitingRoom.createAndShowGui(waitingRoom);
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
