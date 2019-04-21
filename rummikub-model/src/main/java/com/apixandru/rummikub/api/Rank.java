package com.apixandru.rummikub.api;

/**
 * @author Alexandru-Constantin Bledea
 * @since Sep 16, 2015
 */
public enum Rank {

    ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, ELEVEN, TWELVE, THIRTEEN;

    public int asNumber() {
        return ordinal() + 1;
    }

    @Override
    public String toString() {
        return String.format("%2d", asNumber());
    }

}
