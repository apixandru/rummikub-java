/**
 *
 */
package com.github.apixandru.games.rummikub.model;

/**
 * @author Alexandru-Constantin Bledea
 * @since Sep 16, 2015
 */
public final class Card {

    private final int id;
    private final Color color;
    private final Rank rank;

    /**
     * @param id
     * @param color
     * @param rank
     */
    Card(final int id, final Color color, final Rank rank) {
        if (null == color) {
            if (null != rank) {
                throw new IllegalArgumentException();
            }
        } else if (null == rank) {
            throw new IllegalArgumentException();
        }
        this.id = id;
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

    /**
     * @return
     */
    public int getId() {
        return id;
    }

}
