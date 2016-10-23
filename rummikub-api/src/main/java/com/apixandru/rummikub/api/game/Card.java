package com.apixandru.rummikub.api.game;

/**
 * @author Alexandru-Constantin Bledea
 * @since Sep 16, 2015
 */
public final class Card {

    private final Color color;
    private final Rank rank;

    public Card(final Color color, final Rank rank) {
        if (null == color) {
            if (null != rank) {
                throw new IllegalArgumentException();
            }
        } else if (null == rank) {
            throw new IllegalArgumentException();
        }
        this.color = color;
        this.rank = rank;
    }

    public Color getColor() {
        return color;
    }

    public Rank getRank() {
        return rank;
    }

    @Override
    public String toString() {
        return null == rank ? "JOKER" : color + " " + rank;
    }

}
