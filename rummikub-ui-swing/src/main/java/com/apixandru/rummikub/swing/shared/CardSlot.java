package com.apixandru.rummikub.swing.shared;

import org.slf4j.Logger;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.BorderLayout;
import java.awt.Color;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 18, 2015
 */
public final class CardSlot extends JPanel {

    private static final Logger log = getLogger(CardSlot.class);

    public final int x, y;

    public CardSlot(final int x, final int y) {
        super(new BorderLayout());
        setBorder(new LineBorder(Color.LIGHT_GRAY));
        this.x = x;
        this.y = y;
    }

    public boolean isFree() {
        return getComponentCount() == 0;
    }

    public void setCard(CardUi card) {
        log.debug("Add {} to {}", card, this);
        add(card);
        validate();
        repaint();
    }

    public void removeCard(CardUi card) {
        log.debug("Remove {} from {}", card, this);
        remove(card);
        validate();
        repaint();
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", x, y);
    }

}
