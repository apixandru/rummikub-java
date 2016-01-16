/**
 *
 */
package com.apixandru.games.rummikub.swing;

import com.apixandru.games.rummikub.api.Card;
import com.apixandru.utils.swing.SwingUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.*;

import static com.apixandru.games.rummikub.swing.UiConstants.FONT_SCALE;

/**
 * @author apixandru
 * @since Aug 11, 2015
 */
final class UiUtil {

    private static final Logger log = LoggerFactory.getLogger(UiUtil.class);

    public static final Font CARD_FONT = new Font(null, Font.BOLD, (int) (32 * FONT_SCALE));

    /**
     * @param color
     * @return
     */
    public static Color getColor(final com.apixandru.games.rummikub.api.Color color) {
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
        log.debug("Place card request for " + card.card + " on (" + slot.x + ", " + slot.y + ")");
        slot.add(card);
        SwingUtil.setChanged(slot);
    }

    /**
     * @param card
     * @param slot
     */
    public static void placeCard(final Card card, final CardSlot slot) {
        placeCard(CardUi.of(card), slot);
    }

    /**
     * @param slot
     */
    public static void removeCard(final CardSlot slot) {
        slot.removeAll();
        SwingUtil.setChanged(slot);
    }

}
