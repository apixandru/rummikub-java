package com.apixandru.games.rummikub.swing;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.BorderLayout;
import java.awt.Color;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 18, 2015
 */
final class CardSlot extends JPanel {

    final int x, y;

    /**
     * @param x
     * @param y
     */
    CardSlot(final int x, final int y) {
        super(new BorderLayout());
        setBorder(new LineBorder(Color.LIGHT_GRAY));
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }

}
