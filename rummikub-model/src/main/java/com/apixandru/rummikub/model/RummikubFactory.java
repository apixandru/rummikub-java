package com.apixandru.rummikub.model;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 26, 2015
 */
public final class RummikubFactory {

    private RummikubFactory() {
    }

    public static Rummikub<Integer> newInstance() {
        return new RummikubImpl();
    }

}
