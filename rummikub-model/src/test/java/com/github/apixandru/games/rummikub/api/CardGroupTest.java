/**
 *
 */
package com.github.apixandru.games.rummikub.api;

import org.junit.Test;

import java.util.Arrays;

import static com.github.apixandru.games.rummikub.api.TestUtils.card;
import static com.github.apixandru.games.rummikub.api.TestUtils.joker;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Alexandru-Constantin Bledea
 * @since Sep 16, 2015
 */
@SuppressWarnings("static-method")
public final class CardGroupTest {

    /**
     *
     */
    @Test
    public void testTooFewCards2() {
        final Card black1 = card(Color.BLACK, Rank.ONE);
        final Card blue1 = card(Color.BLUE, Rank.ONE);
        final CardGroup group = new CardGroup(Arrays.asList(black1, blue1));
        assertFalse("Less than three cards is always invalid", group.isValid());
    }

    /**
     *
     */
    @Test
    public void testRepeatedColorGroup() {
        final Card black1 = card(Color.BLACK, Rank.ONE);
        final Card blue1 = card(Color.BLUE, Rank.ONE);
        final Card blue1again = card(Color.BLACK, Rank.ONE);
        final CardGroup group = new CardGroup(Arrays.asList(black1, blue1, blue1again));
        assertFalse("Cannot have the same color twice in a group", group.isValid());
    }

    /**
     *
     */
    @Test
    public void testBadRankGroup() {
        final Card black1 = card(Color.BLACK, Rank.ONE);
        final Card blue1 = card(Color.BLUE, Rank.ONE);
        final Card red2 = card(Color.RED, Rank.TWO);
        final CardGroup group = new CardGroup(Arrays.asList(black1, blue1, red2));
        assertFalse("A group must have different colors", group.isValid());
    }

    /**
     *
     */
    @Test
    public void testValidGroup() {
        final Card black1 = card(Color.BLACK, Rank.ONE);
        final Card blue1 = card(Color.BLUE, Rank.ONE);
        final Card red1 = card(Color.RED, Rank.ONE);
        final Card yellow1 = card(Color.YELLOW, Rank.ONE);
        final CardGroup group = new CardGroup(Arrays.asList(black1, blue1, red1, yellow1));
        assertTrue("Should be valid", group.isValid());
    }

    /**
     *
     */
    @Test
    public void testValidGroupJoker() {
        final Card black1 = card(Color.BLACK, Rank.ONE);
        final Card blue1 = card(Color.BLUE, Rank.ONE);
        final Card red1 = card(Color.RED, Rank.ONE);
        final CardGroup group = new CardGroup(Arrays.asList(joker, black1, blue1, red1));
        assertTrue("Should be valid", group.isValid());
    }

    /**
     *
     */
    @Test
    public void testValidRun() {
        final Card black1 = card(Color.BLACK, Rank.ONE);
        final Card black2 = card(Color.BLACK, Rank.TWO);
        final Card black3 = card(Color.BLACK, Rank.THREE);
        final CardGroup group = new CardGroup(Arrays.asList(black1, black2, black3));
        assertTrue("Should be valid", group.isValid());
    }

    /**
     *
     */
    @Test
    public void testRunOverflow() {
        final Card black12 = card(Color.BLACK, Rank.TWELVE);
        final Card black13 = card(Color.BLACK, Rank.THIRTEEN);
        final Card black1 = card(Color.BLACK, Rank.ONE);
        final CardGroup group = new CardGroup(Arrays.asList(black12, black13, black1));
        assertFalse("Should be valid", group.isValid());
    }

    /**
     *
     */
    @Test
    public void testTooManyInGroup() {
        final Card black1 = card(Color.BLACK, Rank.ONE);
        final Card blue1 = card(Color.BLUE, Rank.ONE);
        final Card red1 = card(Color.RED, Rank.ONE);
        final Card yellow1 = card(Color.YELLOW, Rank.ONE);
        final CardGroup group = new CardGroup(Arrays.asList(black1, blue1, red1, yellow1, joker));
        assertFalse("A group can have at most 4 cards", group.isValid());
    }

}