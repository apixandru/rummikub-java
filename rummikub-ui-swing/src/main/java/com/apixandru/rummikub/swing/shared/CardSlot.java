package com.apixandru.rummikub.swing.shared;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.BorderLayout;
import java.awt.Color;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 18, 2015
 */
public final class CardSlot extends JPanel {

    public final int x, y;

    public CardSlot(final int x, final int y) {
        super(new BorderLayout());
        setBorder(new LineBorder(Color.LIGHT_GRAY));
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }

    public boolean isFree() {
        return getComponentCount() == 0;
    }

}
