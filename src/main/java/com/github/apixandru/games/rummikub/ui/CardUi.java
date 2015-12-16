/**
 *
 */
package com.github.apixandru.games.rummikub.ui;

import com.github.apixandru.games.rummikub.model.Card;
import com.github.apixandru.games.rummikub.model.Cards;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * @author Alexandru-Constantin Bledea
 * @since Oct 10, 2015
 */
public final class CardUi extends JPanel {

    private static final Dimension dimension = new Dimension(60, 96);

    private final Color color;
    private final Card card;
    private static final int RADIUS = 20;

    /**
     * @param card
     */
    public CardUi(final Card card) {
        this.card = card;
        setMinimumSize(dimension);
        setMaximumSize(dimension);
        setPreferredSize(dimension);
        setBorder(new LineBorder(Color.BLACK));
        final boolean joker = Cards.isJoker(card);
        final JLabel label = new JLabel();
        label.setFont(UiUtil.CARD_FONT);
        this.color = UiUtil.getColor(card.getColor());
        label.setForeground(color);
        setBackground(Color.WHITE);
        if (joker) {
            label.setText("J");
        } else {
            final int number = card.getRank().ordinal() + 1;
            label.setText(String.valueOf(number));
        }
        add(label);
    }

    /* (non-Javadoc)
     * @see javax.swing.JComponent#paint(java.awt.Graphics)
     */
    @Override
    public void paint(final Graphics g) {
        super.paint(g);
        final Color color = g.getColor();
        g.setColor(this.color);
        g.fillOval((getWidth() - RADIUS) / 2, (int) (getHeight() - RADIUS * 1.7), RADIUS, RADIUS);
        g.setColor(color);
    }
}
