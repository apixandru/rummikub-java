package com.apixandru.games.rummikub.swing;

import com.apixandru.rummikub.StateChangeListener;
import com.apixandru.rummikub.waiting.WaitingRoomConfigurator;

import javax.swing.JFrame;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 09, 2016
 */
public class WindowManager implements StateChangeListener<CardSlot> {

    private JFrame waitingRoomFrame;

    @Override
    public void enteredWaitingRoom(final WaitingRoomConfigurator configurator) {
        final WaitingRoom waitingRoom = new WaitingRoom(configurator.newStartGameListener());
        configurator.registerListener(waitingRoom);
        waitingRoomFrame = WaitingRoom.createAndShowGui(waitingRoom);
    }

}
