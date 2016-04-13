package com.apixandru.games.rummikub.swing;

import com.apixandru.games.rummikub.api.Player;
import com.apixandru.games.rummikub.client.ConnectorBuilder;
import com.apixandru.games.rummikub.client.PlayerCallbackAdapter;
import com.apixandru.rummikub.game.GameConfigurer;
import com.apixandru.utils.swing.ComponentDragSource;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;

import static com.apixandru.games.rummikub.api.Constants.NUM_COLS;
import static com.apixandru.games.rummikub.swing.UiConstants.TILE_HEIGHT;
import static com.apixandru.games.rummikub.swing.UiConstants.TILE_WIDTH;
import static java.lang.Math.max;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 10, 2016
 */
class GameFrame {

    private static final int BOARD_WIDTH = NUM_COLS * TILE_WIDTH;

    static void run(String username, PlayerUi player, final PlayerCallbackAdapter<CardSlot> adapter, final GameConfigurer<CardSlot> configurer) {
        final JFrame frame = new JFrame();
        final JGridPanel board = RummikubUi.newBoard();

        final JButton btnEndTurn = new JButton("End Turn");
        final GameListener callback = new GameListener(frame, board, btnEndTurn, username);

        configurer.addGameEventListener(callback);
        adapter.addGameEventListener(callback);

        adapter.addPlayerCallback(player);

        configurer.addBoardCallback(callback);
        adapter.addBoardCallback(callback);

        adapter.addConnectionListener(callback);

        final Player<CardSlot> actualPlayer =
                ConnectorBuilder.from(player.getAllSlots())
                        .setPlayerName(username)
                        .link(adapter);

        final JPanel comp = createMiddlePanel(btnEndTurn, actualPlayer);
        comp.setBounds(0, 7 * TILE_HEIGHT, BOARD_WIDTH, 60);

        final JLayeredPane layeredPane = new JLayeredPane() {
            @Override
            public String toString() {
                return "the drag layer";
            }
        };
        layeredPane.add(board, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(player, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(comp);

        final ComponentDragSource<CardUi> dragSource = new ComponentDragSource<>(player, board);
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

    private static JPanel createMiddlePanel(final JButton btnEndTurn, final Player<CardSlot> actualPlayer) {
        final JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(Box.createHorizontalGlue());
        btnEndTurn.addActionListener((e) -> actualPlayer.endTurn());
        panel.add(btnEndTurn);
        return panel;
    }

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