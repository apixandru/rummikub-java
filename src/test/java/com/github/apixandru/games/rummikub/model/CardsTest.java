/**
 *
 */
package com.github.apixandru.games.rummikub.model;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.github.apixandru.games.rummikub.model.TestUtils.joker;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Alexandru-Constantin Bledea
 * @since Sep 17, 2015
 */
@SuppressWarnings("static-method")
public final class CardsTest {

    /**
     *
     */
    @Test
    public void testIsAscendingRanks() {
        final List<Card> cards = Arrays.asList(new Card(Color.RED, Rank.ONE), new Card(Color.RED, Rank.TWO), new Card(Color.RED, Rank.THREE));
        assertTrue("Cards should be in ascending order", Cards.isAscendingRanks(cards));
    }

    /**
     *
     */
    @Test
    public void testIsAscendingRanksWithJoker() {
        final List<Card> cards = Arrays.asList(new Card(Color.RED, Rank.ONE), joker, new Card(Color.RED, Rank.THREE));
        assertTrue("Cards should be in ascending order", Cards.isAscendingRanks(cards));
    }

    /**
     *
     */
    @Test
    public void testIsAscendingRanksWithJokerBad() {
        final List<Card> cards = Arrays.asList(new Card(Color.RED, Rank.ONE), joker, new Card(Color.RED, Rank.FOUR));
        assertFalse("Cards should not be in ascending order", Cards.isAscendingRanks(cards));
    }

    /**
     *
     */
    @Test
    public void testIsAscendingRanksBad() {
        final List<Card> cards = Arrays.asList(new Card(Color.RED, Rank.TWELVE), new Card(Color.RED, Rank.THIRTEEN), new Card(Color.RED, Rank.ONE));
        assertFalse("Cards should not be in ascending order", Cards.isAscendingRanks(cards));
    }

    /**
     *
     */
    @Test
    public void testIsAscendingRanksDoubleJoker() {
        final List<Card> cards = Arrays.asList(joker, joker, new Card(Color.RED, Rank.THREE));
        assertTrue("Jokers should map to 1 and 2", Cards.isAscendingRanks(cards));
    }

    /**
     *
     */
    @Test
    public void testIsAscendingRanksDoubleJokerBad() {
        final List<Card> cards = Arrays.asList(joker, joker, new Card(Color.RED, Rank.ONE));
        assertFalse("Jokers should map to 1 and 2, third card cannot be 1", Cards.isAscendingRanks(cards));
    }

    /**
     *
     */
    @Test
    public void testIsSameColorJokers() {
        final List<Card> cards = Arrays.asList(joker, joker);
        assertTrue("Jokers are actually 'the same' color", Cards.isAllSameColor(cards));
    }

    /**
     *
     */
    @Test
    public void testIsSameRankJokers() {
        final List<Card> cards = Arrays.asList(joker, joker);
        assertTrue("Jokers are actually 'the same' color", Cards.isSameRanks(cards));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidConfiguration() {
        new Card(null, Rank.EIGHT);
    }

}
