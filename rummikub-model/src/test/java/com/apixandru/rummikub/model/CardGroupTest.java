package com.apixandru.rummikub.model;

import com.apixandru.rummikub.api.Card;
import com.apixandru.rummikub.api.Color;
import com.apixandru.rummikub.api.Rank;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.apixandru.rummikub.model.CardGroup.isAscendingRanks;
import static com.apixandru.rummikub.model.TestUtils.card;
import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.util.AssertionErrors.assertFalse;
import static org.springframework.test.util.AssertionErrors.assertTrue;

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
        final CardGroup group = new CardGroup(asList(black1, blue1));
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
        final CardGroup group = new CardGroup(asList(black1, blue1, blue1again));
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
        final CardGroup group = new CardGroup(asList(black1, blue1, red2));
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
        final CardGroup group = new CardGroup(asList(black1, blue1, red1, yellow1));
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
        final CardGroup group = new CardGroup(asList(TestUtils.joker, black1, blue1, red1));
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
        final CardGroup group = new CardGroup(asList(black1, black2, black3));
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
        final CardGroup group = new CardGroup(asList(black12, black13, black1));
        assertTrue("Should be valid", group.isValid());
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
        final CardGroup group = new CardGroup(asList(black1, blue1, red1, yellow1, TestUtils.joker));
        assertFalse("A group can have at most 4 cards", group.isValid());
    }

    @Test
    public void testIsAscendingRanks() {
        final List<Card> cards = asList(card(Color.RED, Rank.ONE), card(Color.RED, Rank.TWO), card(Color.RED, Rank.THREE));
        assertTrue("Cards should be in ascending order", isAscendingRanks(cards));
    }

    @Test
    public void testIsAscendingRanksWithJoker() {
        final List<Card> cards = asList(
                card(Color.RED, Rank.ONE),
                TestUtils.joker,
                card(Color.RED, Rank.THREE)
        );
        assertTrue("Cards should be in ascending order", isAscendingRanks(cards));
    }

    @Test
    public void testIsAscendingRanksWithJokerBad() {
        final List<Card> cards = asList(card(Color.RED, Rank.ONE), TestUtils.joker, card(Color.RED, Rank.FOUR));
        assertFalse("Cards should not be in ascending order", isAscendingRanks(cards));
    }

    @Test
    public void testIsAscendingRanksOverflow() {
        final List<Card> cards = asList(
                card(Color.RED, Rank.TWELVE),
                card(Color.RED, Rank.THIRTEEN),
                card(Color.RED, Rank.ONE)
        );
        assertTrue("Cards should not be in ascending order", isAscendingRanks(cards));
    }

    @Test
    public void testIsAscendingRanksDoubleJoker() {
        final List<Card> cards = asList(TestUtils.joker, TestUtils.joker, card(Color.RED, Rank.THREE));
        assertTrue("Jokers should map to 1 and 2", isAscendingRanks(cards));
    }

    @Test
    public void testIsAscendingRanksDoubleJokerBad() {
        final List<Card> cards = asList(
                TestUtils.joker,
                TestUtils.joker,
                card(Color.RED, Rank.ONE)
        );
        assertTrue("Jokers should map to 12 and 13, third card can be 1", isAscendingRanks(cards));
    }

    @Test
    public void testIsSameColorJokers() {
        final List<Card> cards = asList(TestUtils.joker, TestUtils.joker);
        assertTrue("Jokers are actually 'the same' color", CardGroup.isAllSameColor(cards));
    }

    @Test
    public void testIsSameRankJokers() {
        final List<Card> cards = asList(TestUtils.joker, TestUtils.joker);
        assertTrue("Jokers are actually 'the same' color", CardGroup.isSameRanks(cards));
    }

    @Test
    public void testInvalidConfiguration() {
        assertThrows(IllegalArgumentException.class, () -> {
            card(null, Rank.EIGHT);
        });
    }

}
