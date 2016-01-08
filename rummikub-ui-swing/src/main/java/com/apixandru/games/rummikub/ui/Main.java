package com.apixandru.games.rummikub.ui;

import com.apixandru.games.rummikub.api.Player;
import com.apixandru.games.rummikub.client.RummikubConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.IOException;

import static com.apixandru.games.rummikub.api.Constants.NUM_COLS;
import static java.lang.Math.max;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

/**
 * @author Alexandru-Constantin Bledea
 * @since Oct 15, 2015
 */
public final class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    static final int TILE_WIDTH = 60;
    static final int TILE_HEIGHT = 90;
    private static final int BOARD_WIDTH = NUM_COLS * TILE_WIDTH;

    /**
     * @param args
     */
    public static void main(final String[] args) throws IOException {
        if (1 != args.length) {
            log.error("Argument must be server ip.");
            return;
        }

        final String serverIp = args[0];

        final JFrame frame = new JFrame();
        final BoardUi board = new BoardUi();

        final PlayerUi player = new PlayerUi();
        final JButton btnEndTurn = new JButton("End Turn");
        final CardSlotCallback callback = new CardSlotCallback(board, player, btnEndTurn);

        final Player<CardSlot> actualPlayer =
                RummikubConnector.from(callback)
                        .setHints(player.getAllSlots())
                        .setConnectionListener(new SwingConnectionListener(frame))
                        .connectTo(serverIp);

        final JPanel comp = createMiddlePanel(btnEndTurn, actualPlayer);
        comp.setBounds(0, 7 * TILE_HEIGHT, BOARD_WIDTH, 60);

        final JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.add(board, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(player, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(comp);

        final ComponentDragSource dragSource = new ComponentDragSource(player, board);
        final CardDndListener listener = new CardDndListener(dragSource, actualPlayer, callback);

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
