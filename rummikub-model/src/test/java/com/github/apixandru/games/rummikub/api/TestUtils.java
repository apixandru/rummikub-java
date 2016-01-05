package com.github.apixandru.games.rummikub.api;

import java.util.Collection;

import static com.github.apixandru.games.rummikub.api.Color.BLACK;
import static com.github.apixandru.games.rummikub.api.Rank.ONE;
import static org.junit.Assert.assertTrue;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 16, 2015
 */
final class TestUtils {

    static final Card joker = card(null, null);

    static final Card BLACK_ONE_1 = card(BLACK, ONE);
    static final Card BLACK_ONE_2 = card(BLACK, ONE);

    private TestUtils() {
    }

    /**
     * @param color
     * @param rank
     * @return
     */
    static Card card(Color color, Rank rank) {
        return new Card(color, rank);
    }

    /**
     * @param collection
     * @return
     */
    static void assertEmpty(Collection<?> collection) {
        assertTrue(collection.isEmpty());
    }

}
