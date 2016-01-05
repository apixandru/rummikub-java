/**
 *
 */
package com.github.apixandru.games.rummikub.api;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static com.github.apixandru.games.rummikub.api.TestUtils.card;
import static com.github.apixandru.games.rummikub.api.TestUtils.joker;
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
        final List<Card> cards = Arrays.asList(card(Color.RED, Rank.ONE), card(Color.RED, Rank.TWO), card(Color.RED, Rank.THREE));
        assertTrue("Cards should be in ascending order", Cards.isAscendingRanks(cards));
    }

    /**
     *
     */
    @Test
    public void testIsAscendingRanksWithJoker() {
        final List<Card> cards = Arrays.asList(card(Color.RED, Rank.ONE), joker, card(Color.RED, Rank.THREE));
        assertTrue("Cards should be in ascending order", Cards.isAscendingRanks(cards));
    }

    /**
     *
     */
    @Test
    public void testIsAscendingRanksWithJokerBad() {
        final List<Card> cards = Arrays.asList(card(Color.RED, Rank.ONE), joker, card(Color.RED, Rank.FOUR));
        assertFalse("Cards should not be in ascending order", Cards.isAscendingRanks(cards));
    }

    /**
     *
     */
    @Test
    public void testIsAscendingRanksBad() {
        final List<Card> cards = Arrays.asList(card(Color.RED, Rank.TWELVE), card(Color.RED, Rank.THIRTEEN), card(Color.RED, Rank.ONE));
        assertFalse("Cards should not be in ascending order", Cards.isAscendingRanks(cards));
    }

    /**
     *
     */
    @Test
    public void testIsAscendingRanksDoubleJoker() {
        final List<Card> cards = Arrays.asList(joker, joker, card(Color.RED, Rank.THREE));
        assertTrue("Jokers should map to 1 and 2", Cards.isAscendingRanks(cards));
    }

    /**
     *
     */
    @Test
    public void testIsAscendingRanksDoubleJokerBad() {
        final List<Card> cards = Arrays.asList(joker, joker, card(Color.RED, Rank.ONE));
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
        card(null, Rank.EIGHT);
    }

}
