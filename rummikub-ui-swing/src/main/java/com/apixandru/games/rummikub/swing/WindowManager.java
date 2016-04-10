package com.apixandru.games.rummikub.swing;

import com.apixandru.games.rummikub.client.PlayerCallbackAdapter;
import com.apixandru.games.rummikub.client.RummikubConnector;
import com.apixandru.rummikub.StateChangeListener;
import com.apixandru.rummikub.game.GameConfigurer;
import com.apixandru.rummikub.waiting.WaitingRoomConfigurator;

import javax.swing.JFrame;
import java.util.Optional;

import static com.apixandru.games.rummikub.swing.GameFrame.run;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 09, 2016
 */
class WindowManager implements StateChangeListener<CardSlot> {

    private final String username;

    private Optional<JFrame> waitingRoomFrame;
    private RummikubConnector<CardSlot> connector;

    WindowManager(final String username) {
        this.username = username;
    }

    @Override
    public void enteredWaitingRoom(final WaitingRoomConfigurator configurator) {
        final WaitingRoom waitingRoom = new WaitingRoom(configurator.newStartGameListener());
        configurator.registerListener(waitingRoom);
        waitingRoomFrame = Optional.of(WaitingRoom.createAndShowGui(waitingRoom));
    }

    @Override
    public void enteredGame(final GameConfigurer<CardSlot> configurer) {
        waitingRoomFrame.ifPresent(JFrame::dispose);

        final PlayerUi player = new PlayerUi();
        final PlayerCallbackAdapter<CardSlot> adapter = new PlayerCallbackAdapter<>(player.getAllSlots(), connector);

        run(username, player, adapter);
    }

    void setConnector(final RummikubConnector<CardSlot> connector) {
        this.connector = connector;
    }

}
