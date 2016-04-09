package com.apixandru.rummikub;

import com.apixandru.rummikub.waiting.WaitingRoomConfigurator;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 09, 2016
 */
public interface StateChangeListener<H> {

    void enteredWaitingRoom(WaitingRoomConfigurator supplier);

}
