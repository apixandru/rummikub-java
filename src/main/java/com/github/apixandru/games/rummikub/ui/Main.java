package com.github.apixandru.games.rummikub.ui;

import com.github.apixandru.games.rummikub.model.CardPile;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * @author Alexandru-Constantin Bledea
 * @since Oct 15, 2015
 */
public final class Main {

    /**
     * @param args
     */
    public static void main(final String[] args) {
        final JFrame frame = new JFrame();

        final JPanel grid = new JPanel();

        final JLayeredPane layeredPane = new JLayeredPane();
        layeredPane.setPreferredSize(new Dimension(20 * 60, 7 * 96));
        layeredPane.add(grid, JLayeredPane.DEFAULT_LAYER);

        final CardDndListener listener = new CardDndListener(new ComponentDragSource(grid));
        layeredPane.addMouseListener(listener);
        layeredPane.addMouseMotionListener(listener);

        initializeGrid(grid, new CardPile());

        frame.setContentPane(centerHorizontally(layeredPane));

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
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
        final int rows = 7;
        final int cols = 20;
        grid.setLayout(new GridLayout(rows, cols));
        grid.setSize(new Dimension(cols * 60, rows * 96));
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
