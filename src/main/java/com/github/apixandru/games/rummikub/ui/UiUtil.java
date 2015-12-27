/**
 *
 */
package com.github.apixandru.games.rummikub.ui;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

/**
 * @author apixandru
 * @since Aug 11, 2015
 */
public final class UiUtil {

    private static final Logger log = LoggerFactory.getLogger(UiUtil.class);

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

    /**
     * @param card
     * @param slot
     */
    public static void placeCard(final CardUi card, final CardSlot slot) {
        log.debug("Place card request for " + card.card.getId() + " on (" + slot.x + ", " + slot.y + ")");
        slot.add(card);
        slot.validate();
    }

    /**
     * @param slot
     */
    public static void removeCard(final CardSlot slot) {
        slot.removeAll();
        slot.repaint();
    }

}
