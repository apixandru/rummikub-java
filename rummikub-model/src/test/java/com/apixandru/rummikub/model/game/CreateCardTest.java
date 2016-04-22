/**
 *
 */
package com.apixandru.rummikub.model.game;

import com.apixandru.rummikub.api.Color;
import com.apixandru.rummikub.api.Rank;
import org.junit.Test;

/**
 * @author Alexandru-Constantin Bledea
 * @since Sep 16, 2015
 */
@SuppressWarnings({"unused", "static-method"})
public final class CreateCardTest {

    @Test(expected = IllegalArgumentException.class)
    public void testNoColorRank() {
        TestUtils.card(null, Rank.EIGHT);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testColorNoRank() {
        TestUtils.card(Color.BLACK, null);
    }

    @Test
    public void testColorRank() {
        TestUtils.card(Color.BLACK, Rank.ONE);
    }

    @Test
    public void testNoColorNoRank() {
        TestUtils.card(null, null);
    }

}
