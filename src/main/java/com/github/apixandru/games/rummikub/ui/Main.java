package com.github.apixandru.games.rummikub.ui;

import com.github.apixandru.games.rummikub.model.Card;
import com.github.apixandru.games.rummikub.model.CardPile;
import com.github.apixandru.games.rummikub.model.RummikubGame;
import com.github.apixandru.games.rummikub.model2.Player;
import com.github.apixandru.games.rummikub.model2.Rummikub;
import com.github.apixandru.games.rummikub.model2.RummikubCallback;
import com.github.apixandru.games.rummikub.model2.RummikubFactory;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static com.github.apixandru.games.rummikub.model.Constants.NUM_COLS;
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
    public static void main(final String[] args) {
        final Rummikub rummikub = RummikubFactory.newInstance();
        final JFrame frame = new JFrame();
        final RummikubGame rummikubGame = new RummikubGame();
        final BoardUi board = BoardUi.createBoardUi(rummikubGame.getBoard());
        final PlayerUi player = PlayerUi.createPlayerUi(rummikubGame.addPlayer());
        final Player actualPlayer = rummikub.addPlayer("John", new RummikubCallback() {
            @Override
            public void cardReceived(final Card card) {
                placeCardOnBoard(card, player);
            }
        });
        final CardPile pile = new CardPile();
        final JPanel comp = createMiddlePanel(pile, player, actualPlayer);
        comp.setBounds(0, 7 * TILE_HEIGHT, BOARD_WIDTH, 60);

        final JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.add(board, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(player, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(comp);

        final CardDndListener listener = new CardDndListener(new ComponentDragSource(player, board));
        layeredPane.addMouseListener(listener);
        layeredPane.addMouseMotionListener(listener);

        layeredPane.setPreferredSize(computeSize(layeredPane));

        frame.setContentPane(layeredPane);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


    }

    private static JPanel createMiddlePanel(final CardPile pile, final JPanel player, final Player actualPlayer) {
        final JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(Box.createHorizontalGlue());
        final JButton btnEndTurn = new JButton("End Turn");
        btnEndTurn.addActionListener((e) -> actualPlayer.endTurn());
        final JButton btnTakeCard = new JButton("Take Card");
        btnTakeCard.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(final MouseEvent e) {
                placeCardOnBoard(pile.nextCard(), player);
            }
        });
        panel.add(btnEndTurn);
        panel.add(Box.createHorizontalStrut(10));
        panel.add(btnTakeCard);
        return panel;
    }

    /**
     * @param card
     * @param player
     */
    private static void placeCardOnBoard(final Card card, final JPanel player) {
        for (final Component component : player.getComponents()) {
            final JPanel panel = (JPanel) component;
            if (0 == panel.getComponents().length) {
                SwingUtilities.invokeLater(() -> {
                    panel.add(new CardUi(card));
                    player.validate();
                });
                return;
            }
        }
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
