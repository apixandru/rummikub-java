package com.apixandru.rummikub.server;

import com.apixandru.rummikub.brotocol.room.StartGameListener;
import com.apixandru.rummikub.model.Rummikub;
import com.apixandru.rummikub.server.waiting.Room;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 09, 2016
 */
interface StateChangeListener {

    void enteredWaitingRoom(Room room, StartGameListener startGameListener);

    void enteredGame(Rummikub<Integer> rummikub);

}
