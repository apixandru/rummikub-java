package com.apixandru.rummikub.model;

import com.apixandru.rummikub.api.game.Card;
import com.apixandru.rummikub.api.game.Color;
import com.apixandru.rummikub.api.game.Rank;

import java.util.Collection;

import static com.apixandru.rummikub.api.game.Color.BLACK;
import static com.apixandru.rummikub.api.game.Rank.ONE;
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

    static Card card(Color color, Rank rank) {
        return new Card(color, rank);
    }

    static void assertEmpty(Collection<?> collection) {
        assertTrue(collection.isEmpty());
    }

}
