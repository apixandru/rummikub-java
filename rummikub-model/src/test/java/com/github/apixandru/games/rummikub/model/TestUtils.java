package com.github.apixandru.games.rummikub.model;

import static com.github.apixandru.games.rummikub.model.Color.BLACK;
import static com.github.apixandru.games.rummikub.model.Rank.ONE;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 16, 2015
 */
final class TestUtils {

    private static int idGenerator = 0;

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
        return new Card(idGenerator++, color, rank);
    }

}
