package com.apixandru.rummikub.swing;

import com.apixandru.rummikub.api.config.GameConfigurer;
import com.apixandru.rummikub.api.config.RummikubRoomConfigurer;
import com.apixandru.rummikub.api.config.StateChangeListener;

import javax.swing.JFrame;
import java.util.Optional;

import static com.apixandru.rummikub.swing.GameFrame.run;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 09, 2016
 */
class WindowManager implements StateChangeListener {

    private final String username;

    private Optional<JFrame> waitingRoomFrame;

    WindowManager(final String username) {
        this.username = username;
    }

    @Override
    public void enteredWaitingRoom(final RummikubRoomConfigurer configurer) {
        final WaitingRoom waitingRoom = new WaitingRoom(configurer);
        configurer.registerListener(waitingRoom);
        waitingRoomFrame = Optional.of(WaitingRoom.createAndShowGui(waitingRoom));
    }

    @Override
    public void enteredGame(final GameConfigurer configurer) {
        waitingRoomFrame.ifPresent(JFrame::dispose);

        run(username, configurer);
    }

}
