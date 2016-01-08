/**
 *
 */
package com.apixandru.games.rummikub.api;

/**
 * @author Alexandru-Constantin Bledea
 * @since Sep 16, 2015
 */
public final class Card {

    private final Color color;
    private final Rank rank;

    /**
     * @param color
     * @param rank
     */
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

    /**
     * @return the color
     */
    public Color getColor() {
        return color;
    }

    /**
     * @return the rank
     */
    public Rank getRank() {
        return rank;
    }

    @Override
    public String toString() {
        return null == rank ? "JOKER" : rank + " " + color;
    }

}
