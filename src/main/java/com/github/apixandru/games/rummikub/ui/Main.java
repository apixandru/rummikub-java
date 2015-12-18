package com.github.apixandru.games.rummikub.ui;

import com.github.apixandru.games.rummikub.model.CardPile;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

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

        final JPanel board = new JPanel();
        final JPanel player = new JPanel();
        panel.add(board);
        final JPanel comp = createMiddlePanel(new CardPile());
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

        initializeGrid(board, new CardPile());
        initializeGrid(player, new CardPile(), 3, 7);


        layeredPane.setPreferredSize(computeSize(layeredPane));

        frame.setContentPane(layeredPane);
//        frame.setContentPane(centerHorizontally(layeredPane));
//        frame.add(panel);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static JPanel createMiddlePanel(final CardPile pile) {
        final JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.add(Box.createHorizontalGlue());
        final JButton skippy = new JButton("Skippy");
        System.out.println(skippy.getSize());
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
            System.out.println(height);
        }
        return new Dimension(width, height);
    }

    /**
     * @param contentToCenter
     * @return
     */
    private static JPanel centerHorizontally(final Component contentToCenter) {
        final JPanel result = new JPanel();
        result.setLayout(new BoxLayout(result, BoxLayout.X_AXIS));
        result.add(Box.createHorizontalGlue());
        result.add(contentToCenter);
        result.add(Box.createHorizontalGlue());
        return result;
    }

    /**
     * @param grid
     * @param cardPile
     */
    private static void initializeGrid(final JPanel grid, final CardPile cardPile) {
        initializeGrid(grid, cardPile, 7, 0);
    }

    /**
     * @param grid
     * @param cardPile
     */
    private static void initializeGrid(final JPanel grid, final CardPile cardPile, final int rows, final int y) {
        final int cols = 20;
        grid.setLayout(new GridLayout(rows, cols));
        grid.setBounds(0, y == 0 ? 0 : y * TILE_HEIGHT + 60, cols * TILE_WIDTH, rows * TILE_HEIGHT);
        boolean first = true;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                final JPanel square = new JPanel(new BorderLayout());
                square.setBorder(new LineBorder(Color.LIGHT_GRAY));
                grid.add(square);
                if (first) {
                    first = false;
                    square.add(new CardUi(cardPile.nextCard()));
                }
            }
        }
    }

}
