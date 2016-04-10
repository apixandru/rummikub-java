package com.apixandru.games.rummikub.swing;

import com.apixandru.rummikub.StateChangeListener;
import com.apixandru.rummikub.game.GameConfigurer;
import com.apixandru.rummikub.waiting.WaitingRoomConfigurator;

import javax.swing.JFrame;
import java.util.Optional;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 09, 2016
 */
class WindowManager implements StateChangeListener<CardSlot> {

    private Optional<JFrame> waitingRoomFrame;

    @Override
    public void enteredWaitingRoom(final WaitingRoomConfigurator configurator) {
        final WaitingRoom waitingRoom = new WaitingRoom(configurator.newStartGameListener());
        configurator.registerListener(waitingRoom);
        waitingRoomFrame = Optional.of(WaitingRoom.createAndShowGui(waitingRoom));
    }

    @Override
    public void enteredGame(final GameConfigurer<CardSlot> configurer) {
        waitingRoomFrame.ifPresent(JFrame::dispose);
    }

}
