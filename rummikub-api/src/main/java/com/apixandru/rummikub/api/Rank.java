/**
 *
 */
package com.apixandru.rummikub.api;

/**
 * @author Alexandru-Constantin Bledea
 * @since Sep 16, 2015
 */
public enum Rank {

    ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, ELEVEN, TWELVE, THIRTEEN;

    @Override
    public String toString() {
        return String.format("%2d", ordinal() + 1);
    }

}
