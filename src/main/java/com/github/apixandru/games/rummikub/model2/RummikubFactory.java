package com.github.apixandru.games.rummikub.model2;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 26, 2015
 */
public final class RummikubFactory {

    /**
     *
     */
    private RummikubFactory() {
    }

    /**
     * @return
     */
    public static Rummikub newInstance() {
        return new RummikubImpl();
    }

}
