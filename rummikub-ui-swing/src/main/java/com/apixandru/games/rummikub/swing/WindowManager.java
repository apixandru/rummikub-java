package com.apixandru.games.rummikub.swing;

import com.apixandru.games.rummikub.client.PlayerCallbackAdapter;
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
    private final PlayerUi player;
    private PlayerCallbackAdapter<CardSlot> adapter;

    private Optional<JFrame> waitingRoomFrame;

    WindowManager(final String username, final PlayerUi player) {
        this.username = username;
        this.player = player;
    }

    void setAdapter(final PlayerCallbackAdapter<CardSlot> adapter) {
        this.adapter = adapter;
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
        run(username, player, adapter);
    }

}
