/**
 *
 */
package com.github.apixandru.games.rummikub.model;

/**
 * @author Alexandru-Constantin Bledea
 * @since Sep 16, 2015
 */
public enum Rank {

    ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, ELEVEN, TWELVE, THIRTEEN;

    /**
     * @return
     */
    public static Rank next(final Rank rank) {
        if (null == rank || rank.ordinal() >= values().length - 1) {
            return null;
        }
        return values()[rank.ordinal() + 1];
    }

}
