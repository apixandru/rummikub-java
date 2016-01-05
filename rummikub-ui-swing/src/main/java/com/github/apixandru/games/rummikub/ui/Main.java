package com.github.apixandru.games.rummikub.ui;

import com.github.apixandru.games.rummikub.client.RummikubGame;
import com.github.apixandru.games.rummikub.api.Player;
import com.github.apixandru.games.rummikub.api.PlayerCallback;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;
import java.util.Collections;

import static com.github.apixandru.games.rummikub.api.Constants.NUM_COLS;
import static java.lang.Math.max;

/**
 * @author Alexandru-Constantin Bledea
 * @since Oct 15, 2015
 */
public final class Main {

    static final int TILE_WIDTH = 60;
    static final int TILE_HEIGHT = 90;
    private static final int BOARD_WIDTH = NUM_COLS * TILE_WIDTH;

    /**
     * @param args
     */
    public static void main(final String[] args) throws IOException {
        final JFrame frame = new JFrame();
        final BoardUi board = new BoardUi();

        final PlayerUi player = new PlayerUi();
        final PlayerCallback<CardSlot> callback = new CardSlotCallback(board, player);
        final Player<CardSlot> actualPlayer = RummikubGame.connect(callback, Collections.emptyList());
//        final Player<CardSlot> actualPlayer = RummikubFactory.newInstance().addPlayer("John", callback);

        final JPanel comp = createMiddlePanel(actualPlayer);
        comp.setBounds(0, 7 * TILE_HEIGHT, BOARD_WIDTH, 60);

        final JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.add(board, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(player, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(comp);

        final CardDndListener listener = new CardDndListener(new ComponentDragSource(player, board), actualPlayer);
        layeredPane.addMouseListener(listener);
        layeredPane.addMouseMotionListener(listener);

        layeredPane.setPreferredSize(computeSize(layeredPane));

        frame.setContentPane(layeredPane);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


    }

    /**
     * @param actualPlayer
     * @return
     */
    private static JPanel createMiddlePanel(final Player<CardSlot> actualPlayer) {
        final JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(Box.createHorizontalGlue());
        final JButton btnEndTurn = new JButton("End Turn");
        btnEndTurn.addActionListener((e) -> actualPlayer.endTurn());
        panel.add(btnEndTurn);
        return panel;
    }

    /**
     * @param container
     * @return
     */
    private static Dimension computeSize(Container container) {
        int width = 0;
        int height = 0;
        for (Component component : container.getComponents()) {
            width = max(component.getWidth(), width);
            height += component.getHeight();
        }
        return new Dimension(width, height);
    }

}
