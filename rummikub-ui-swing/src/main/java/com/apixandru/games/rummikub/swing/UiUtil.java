/**
 *
 */
package com.apixandru.games.rummikub.swing;

import com.apixandru.games.rummikub.api.Card;
import com.apixandru.utils.swing.SwingUtil;

import java.awt.Color;
import java.awt.Font;

import static com.apixandru.games.rummikub.swing.UiConstants.FONT_SCALE;

/**
 * @author apixandru
 * @since Aug 11, 2015
 */
final class UiUtil {

    public static final Font CARD_FONT = new Font(null, Font.BOLD, (int) (32 * FONT_SCALE));

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

    public static void placeCard(final Card card, final CardSlot slot) {
        placeCard(CardUi.of(card), slot);
    }

    public static void placeCard(final CardUi card, final CardSlot slot) {
        SwingUtil.addAndNotify(card, slot);
    }

    public static void removeCard(final Card card, final CardSlot slot) {
        SwingUtil.removeAndNotify(CardUi.of(card), slot);
    }

}
