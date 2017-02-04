package com.apixandru.rummikub.swing;

import com.apixandru.rummikub.api.Card;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Alexandru-Constantin Bledea
 * @since Oct 10, 2015
 */
final class CardUi extends JPanel {

    private static final Map<Card, CardUi> cash = new HashMap<>();

    private static final Dimension dimension = new Dimension(60, 96);
    private static final int RADIUS = (int) (20 * UiConstants.scale);
    final Card card;
    private final Color color;

    private CardUi(final Card card) {
        this.card = card;
        setMinimumSize(dimension);
        setMaximumSize(dimension);
        setPreferredSize(dimension);
        setBorder(new LineBorder(Color.BLACK));
        final boolean joker = isJoker(card);
        final JLabel label = new JLabel();
        label.setFont(UiUtil.CARD_FONT);
        this.color = UiUtil.getColor(card.getColor());
        label.setForeground(color);
        setBackground(Color.WHITE);
        if (joker) {
            label.setText("J");
        } else {
            final int number = card.getRank().asNumber();
            label.setText(String.valueOf(number));
        }
        add(label);
    }

    private static boolean isJoker(final Card card) {
        return card.getRank() == null && card.getColor() == null;
    }

    static CardUi of(final Card card) {
        if (cash.containsKey(card)) {
            return cash.get(card);
        }
        final CardUi cardUi = new CardUi(card);
        cash.put(card, cardUi);
        return cardUi;
    }

    @Override
    public void paint(final Graphics g) {
        super.paint(g);
        final Color color = g.getColor();
        g.setColor(this.color);
        g.fillOval((getWidth() - RADIUS) / 2, (int) (getHeight() - RADIUS * 1.7), RADIUS, RADIUS);
        g.setColor(color);
    }

    @Override
    public String toString() {
        return card.toString();
    }

}
