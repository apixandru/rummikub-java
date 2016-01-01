package com.github.apixandru.games.rummikub.model;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 16, 2015
 */
final class TestUtils {

    private static int idGenerator = 0;

    static final Card joker = card(null, null);

    private TestUtils() {
    }

    /**
     * @param color
     * @param rank
     * @return
     */
    static Card card(Color color, Rank rank) {
        return new Card(idGenerator++, color, rank);
    }

}
