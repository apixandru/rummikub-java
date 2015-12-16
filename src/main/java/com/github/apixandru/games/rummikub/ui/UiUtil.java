/**
 *
 */
package com.github.apixandru.games.rummikub.ui;

import javax.swing.*;
import java.awt.*;

/**
 * @author apixandru
 * @since Aug 11, 2015
 */
public final class UiUtil {

    public static final Font CARD_FONT = new Font(null, Font.BOLD, 32);


    /**
     * @param component
     * @param color
     */
    public static void setBackground(final Component component, final Color color) {
        if (null != component) {
            component.setBackground(color);
        }
    }

    /**
     * @param component
     * @return
     */
    public static Color getBackground(final Component component) {
        if (null == component) {
            return null;
        }
        return component.getBackground();
    }

    /**
     * @param c
     * @param d
     * @return
     */
    public static Dimension max(final Dimension c, final Dimension d) {
        if (c.getWidth() > d.getWidth() && c.getHeight() > d.getHeight()) {
            return c;
        }
        return d;
    }

    /**
     * @param color
     * @return
     */
    public static Color getColor(final com.github.apixandru.games.rummikub.model.Color color) {
        if (null == color) {
            return Color.BLACK;
        }
        switch (color) {
            case BLACK:
                return Color.BLACK;
            case BLUE:
                return Color.BLUE;
            case RED:
                return Color.RED;
            case YELLOW:
                return Color.ORANGE;
            default:
                throw new IllegalArgumentException(String.valueOf(color));
        }
    }

    /**
     * @param rows
     * @param cols
     * @param preferredSize
     * @return
     */
    public static final JComponent[][] createGridOfSquares(final int rows, final int cols, final Dimension preferredSize) {
        final JComponent[][] grid = new JComponent[rows][cols];
        for (int x = 0; x < cols; x++) {
            for (int y = 0; y < rows; y++) {
                final JComponent b = new JButton();
                b.setPreferredSize(preferredSize);
                grid[y][x] = b;
            }
        }
        return grid;
    }

}
