/**
 *
 */
package com.github.apixandru.games.rummikub.ui;

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

}
