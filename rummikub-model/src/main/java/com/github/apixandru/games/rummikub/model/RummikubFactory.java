package com.github.apixandru.games.rummikub.model;

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
        return newInstance(null);
    }

    /**
     * @param callback
     * @return
     */
    public static Rummikub newInstance(final BoardCallback callback) {
        return new RummikubImpl(callback);
    }

}
