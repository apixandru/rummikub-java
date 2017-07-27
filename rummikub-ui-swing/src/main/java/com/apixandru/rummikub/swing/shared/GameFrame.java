package com.apixandru.rummikub.swing.shared;

import com.apixandru.rummikub.api.Player;
import com.apixandru.rummikub.client.game.GameConfigurer;
import com.apixandru.rummikub.swing.shared.CardDndListener;
import com.apixandru.rummikub.swing.shared.CardSlot;
import com.apixandru.rummikub.swing.shared.CardSlotIndexConverter;
import com.apixandru.rummikub.swing.shared.CardSlotPlayer;
import com.apixandru.rummikub.swing.shared.CardSlotPlayerCallback;
import com.apixandru.rummikub.swing.shared.CardUi;
import com.apixandru.rummikub.swing.shared.GameListener;
import com.apixandru.rummikub.swing.shared.JGridPanel;
import com.apixandru.rummikub.swing.shared.PlayerUi;
import com.apixandru.rummikub.swing.shared.RummikubUi;
import com.apixandru.rummikub.swing.shared.UiConstants;
import com.apixandru.rummikub.swing.util.ComponentDragSource;

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

import static com.apixandru.rummikub.api.Constants.NUM_COLS;
import static java.lang.Math.max;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

/**
 * @author Alexandru-Constantin Bledea
 * @since April 10, 2016
 */
public final class GameFrame {

    private static final int BOARD_WIDTH = NUM_COLS * UiConstants.TILE_WIDTH;

    public static JFrame run(String username, final GameConfigurer configurer) {
        final JFrame frame = new JFrame();
        final JGridPanel board = RummikubUi.newBoard();

        final JButton btnEndTurn = new JButton("End Turn");
        final GameListener callback = new GameListener(frame, board, btnEndTurn, username);

        final PlayerUi player = new PlayerUi();
        final Player<CardSlot> actualPlayer = getNewPlayer(player, configurer, callback);

        final JPanel comp = createMiddlePanel(btnEndTurn, actualPlayer);
        comp.setBounds(0, 7 * UiConstants.TILE_HEIGHT, BOARD_WIDTH, 60);

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

        return frame;
    }

    private static Player<CardSlot> getNewPlayer(PlayerUi player, GameConfigurer configurer, GameListener callback) {
        final CardSlotIndexConverter converter = new CardSlotIndexConverter(player.getAllSlots());

        configurer.addGameEventListener(callback);
        configurer.addBoardListener(callback);

        return new CardSlotPlayer(configurer.newPlayer(new CardSlotPlayerCallback(player, converter)), converter);
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
