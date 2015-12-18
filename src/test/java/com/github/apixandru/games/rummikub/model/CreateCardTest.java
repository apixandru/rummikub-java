/**
 *
 */
package com.github.apixandru.games.rummikub.model;

import org.junit.Test;

import static com.github.apixandru.games.rummikub.model.TestUtils.card;

/**
 * @author Alexandru-Constantin Bledea
 * @since Sep 16, 2015
 */
@SuppressWarnings({"unused", "static-method"})
public final class CreateCardTest {

    @Test(expected = IllegalArgumentException.class)
    public void testNoColorRank() {
        card(null, Rank.EIGHT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testColorNoRank() {
        card(Color.BLACK, null);
    }

    @Test
    public void testColorRank() {
        card(Color.BLACK, Rank.ONE);
    }

    @Test
    public void testNoColorNoRank() {
        card(null, null);
    }

}
