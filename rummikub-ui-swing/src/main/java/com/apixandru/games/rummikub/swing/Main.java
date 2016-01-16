package com.apixandru.games.rummikub.swing;

import com.apixandru.games.rummikub.api.Player;
import com.apixandru.games.rummikub.client.RummikubConnector;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;

import static com.apixandru.games.rummikub.api.Constants.NUM_COLS;
import static com.apixandru.games.rummikub.swing.UiConstants.TILE_HEIGHT;
import static com.apixandru.games.rummikub.swing.UiConstants.TILE_WIDTH;
import static java.lang.Math.max;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

/**
 * @author Alexandru-Constantin Bledea
 * @since Oct 15, 2015
 */
public final class Main {

    private static final int BOARD_WIDTH = NUM_COLS * TILE_WIDTH;

    /**
     * @param args
     */
    public static void main(final String[] args) throws IOException {
        final ServerData.ConnectionData connectionData = ServerData.getConnectionData();
        if (null == connectionData) {
            return;
        }

        final JFrame frame = new JFrame();
        final JGridPanel board = RummikubUi.newBoard();

        final JButton btnEndTurn = new JButton("End Turn");
        final GameListener callback = new GameListener(frame, board, btnEndTurn);

        final PlayerUi player = new PlayerUi();

        final Player<CardSlot> actualPlayer =
                RummikubConnector.from(player)
                        .setBoardCallback(callback)
                        .setGameEventListener(callback)
                        .setConnectionListener(callback)
                        .setHints(player.getAllSlots())
                        .setPlayerName(connectionData.username)
                        .link(connectionData.socket);

        final JPanel comp = createMiddlePanel(btnEndTurn, actualPlayer);
        comp.setBounds(0, 7 * TILE_HEIGHT, BOARD_WIDTH, 60);

        final JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.add(board, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(player, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(comp);

        final ComponentDragSource dragSource = new ComponentDragSource(player, board);
        final CardDndListener listener = new CardDndListener(dragSource, board, actualPlayer, callback);

        layeredPane.addMouseListener(listener);
        layeredPane.addMouseMotionListener(listener);

        layeredPane.setPreferredSize(computeSize(layeredPane));

        frame.setContentPane(layeredPane);
        frame.pack();
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


    }

    /**
     * @param btnEndTurn
     * @param actualPlayer
     * @return
     */
    private static JPanel createMiddlePanel(final JButton btnEndTurn, final Player<CardSlot> actualPlayer) {
        final JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(Box.createHorizontalGlue());
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
