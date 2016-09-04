/**
 *
 */
package com.apixandru.rummikub.model.game;

import com.apixandru.rummikub.api.game.Card;
import com.apixandru.rummikub.api.game.Color;
import com.apixandru.rummikub.api.game.Rank;
import org.junit.Test;

import java.util.Arrays;

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
        final Card black1 = TestUtils.card(Color.BLACK, Rank.ONE);
        final Card blue1 = TestUtils.card(Color.BLUE, Rank.ONE);
        final CardGroup group = new CardGroup(Arrays.asList(black1, blue1));
        assertFalse("Less than three cards is always invalid", group.isValid());
    }

    /**
     *
     */
    @Test
    public void testRepeatedColorGroup() {
        final Card black1 = TestUtils.card(Color.BLACK, Rank.ONE);
        final Card blue1 = TestUtils.card(Color.BLUE, Rank.ONE);
        final Card blue1again = TestUtils.card(Color.BLACK, Rank.ONE);
        final CardGroup group = new CardGroup(Arrays.asList(black1, blue1, blue1again));
        assertFalse("Cannot have the same color twice in a group", group.isValid());
    }

    /**
     *
     */
    @Test
    public void testBadRankGroup() {
        final Card black1 = TestUtils.card(Color.BLACK, Rank.ONE);
        final Card blue1 = TestUtils.card(Color.BLUE, Rank.ONE);
        final Card red2 = TestUtils.card(Color.RED, Rank.TWO);
        final CardGroup group = new CardGroup(Arrays.asList(black1, blue1, red2));
        assertFalse("A group must have different colors", group.isValid());
    }

    /**
     *
     */
    @Test
    public void testValidGroup() {
        final Card black1 = TestUtils.card(Color.BLACK, Rank.ONE);
        final Card blue1 = TestUtils.card(Color.BLUE, Rank.ONE);
        final Card red1 = TestUtils.card(Color.RED, Rank.ONE);
        final Card yellow1 = TestUtils.card(Color.YELLOW, Rank.ONE);
        final CardGroup group = new CardGroup(Arrays.asList(black1, blue1, red1, yellow1));
        assertTrue("Should be valid", group.isValid());
    }

    /**
     *
     */
    @Test
    public void testValidGroupJoker() {
        final Card black1 = TestUtils.card(Color.BLACK, Rank.ONE);
        final Card blue1 = TestUtils.card(Color.BLUE, Rank.ONE);
        final Card red1 = TestUtils.card(Color.RED, Rank.ONE);
        final CardGroup group = new CardGroup(Arrays.asList(TestUtils.joker, black1, blue1, red1));
        assertTrue("Should be valid", group.isValid());
    }

    /**
     *
     */
    @Test
    public void testValidRun() {
        final Card black1 = TestUtils.card(Color.BLACK, Rank.ONE);
        final Card black2 = TestUtils.card(Color.BLACK, Rank.TWO);
        final Card black3 = TestUtils.card(Color.BLACK, Rank.THREE);
        final CardGroup group = new CardGroup(Arrays.asList(black1, black2, black3));
        assertTrue("Should be valid", group.isValid());
    }

    /**
     *
     */
    @Test
    public void testRunOverflow() {
        final Card black12 = TestUtils.card(Color.BLACK, Rank.TWELVE);
        final Card black13 = TestUtils.card(Color.BLACK, Rank.THIRTEEN);
        final Card black1 = TestUtils.card(Color.BLACK, Rank.ONE);
        final CardGroup group = new CardGroup(Arrays.asList(black12, black13, black1));
        assertFalse("Should be valid", group.isValid());
    }

    /**
     *
     */
    @Test
    public void testTooManyInGroup() {
        final Card black1 = TestUtils.card(Color.BLACK, Rank.ONE);
        final Card blue1 = TestUtils.card(Color.BLUE, Rank.ONE);
        final Card red1 = TestUtils.card(Color.RED, Rank.ONE);
        final Card yellow1 = TestUtils.card(Color.YELLOW, Rank.ONE);
        final CardGroup group = new CardGroup(Arrays.asList(black1, blue1, red1, yellow1, TestUtils.joker));
        assertFalse("A group can have at most 4 cards", group.isValid());
    }

}
