package com.apixandru.rummikub.model;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 26, 2015
 */
final class RummikubFactory {

    private RummikubFactory() {
    }

    static Rummikub<Integer> newInstance() {
        return new RummikubImpl();
    }

}
