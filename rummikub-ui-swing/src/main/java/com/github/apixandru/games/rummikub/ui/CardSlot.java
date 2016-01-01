package com.github.apixandru.games.rummikub.ui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 18, 2015
 */
public final class CardSlot extends JPanel {

    final int x, y;

    /**
     * @param x
     * @param y
     */
    public CardSlot(final int x, final int y) {
        super(new BorderLayout());
        setBorder(new LineBorder(Color.LIGHT_GRAY));
        this.x = x;
        this.y = y;
    }

}
