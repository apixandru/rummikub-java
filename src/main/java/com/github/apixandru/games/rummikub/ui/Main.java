package com.github.apixandru.games.rummikub.ui;

import com.github.apixandru.games.rummikub.model.CardPile;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import static java.lang.Math.max;

/**
 * @author Alexandru-Constantin Bledea
 * @since Oct 15, 2015
 */
public final class Main {

    private static final int TILE_WIDTH = 60;
    private static final int TILE_HEIGHT = 96;
    private static final int BOARD_WIDTH = 20 * TILE_WIDTH;

    /**
     * @param args
     */
    public static void main(final String[] args) {
        final JFrame frame = new JFrame();

        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        final BoardUi board = new BoardUi();
        final JPanel player = new JPanel();
        panel.add(board);
        final CardPile pile = new CardPile();
        final JPanel comp = createMiddlePanel(pile, player);
        comp.setBounds(0, 7 * TILE_HEIGHT, BOARD_WIDTH, 60);
        panel.add(comp);
        panel.add(player);

        final JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.add(board, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(player, JLayeredPane.DEFAULT_LAYER);
        layeredPane.add(comp);

        final CardDndListener listener = new CardDndListener(new ComponentDragSource(player, board));
        layeredPane.addMouseListener(listener);
        layeredPane.addMouseMotionListener(listener);

        initializeGrid(board);
        initializeGrid(player, 3, 7);


        layeredPane.setPreferredSize(computeSize(layeredPane));

        frame.setContentPane(layeredPane);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static JPanel createMiddlePanel(final CardPile pile, final JPanel player) {
        final JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(Box.createHorizontalGlue());
        final JButton skippy = new JButton("Take Card");
        skippy.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(final MouseEvent e) {
                final Component[] components = player.getComponents();
                for (final Component component : components) {
                    final JPanel panel = (JPanel) component;
                    if (0 == panel.getComponents().length) {
                        SwingUtilities.invokeLater(() -> {
                            panel.add(new CardUi(pile.nextCard()));
                            player.invalidate();
                            player.repaint();
                            player.validate();
                        });
                        return;
                    }
                }
            }
        });
        panel.add(skippy);
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

    /**
     * @param grid
     */
    private static void initializeGrid(final JPanel grid) {
        initializeGrid(grid, 7, 0);
    }

    /**
     * @param grid
     * @param rows
     * @param y
     */
    private static void initializeGrid(final JPanel grid, final int rows, final int y) {
        final int cols = 20;
        grid.setLayout(new GridLayout(rows, cols));
        grid.setBounds(0, y == 0 ? 0 : y * TILE_HEIGHT + 60, cols * TILE_WIDTH, rows * TILE_HEIGHT);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid.add(new CardSlot(j, i));
            }
        }
    }

}
