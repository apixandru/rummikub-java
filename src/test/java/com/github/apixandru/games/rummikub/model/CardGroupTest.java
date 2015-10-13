/**
 *
 */
package com.github.apixandru.games.rummikub.model;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import com.github.apixandru.games.rummikub.model.Card;
import com.github.apixandru.games.rummikub.model.CardGroup;
import com.github.apixandru.games.rummikub.model.Color;
import com.github.apixandru.games.rummikub.model.Rank;

/**
 * @author Alexandru-Constantin Bledea
 * @since Sep 16, 2015
 */
@SuppressWarnings ("static-method")
public final class CardGroupTest {

	private static final Card joker = new Card(null, null);

	/**
	 *
	 */
	@Test
	public void testTooFewCards2() {
		final Card black1 = new Card(Color.BLACK, Rank.ONE);
		final Card blue1 = new Card(Color.BLUE, Rank.ONE);
		final CardGroup group = new CardGroup(Arrays.asList(black1, blue1));
		Assert.assertFalse("Less than three cards is always invalid", group.isValid());
	}

	/**
	 *
	 */
	@Test
	public void testRepeatedColorGroup() {
		final Card black1 = new Card(Color.BLACK, Rank.ONE);
		final Card blue1 = new Card(Color.BLUE, Rank.ONE);
		final Card blue1again = new Card(Color.BLACK, Rank.ONE);
		final CardGroup group = new CardGroup(Arrays.asList(black1, blue1, blue1again));
		Assert.assertFalse("Cannot have the same color twice in a group", group.isValid());
	}

	/**
	 *
	 */
	@Test
	public void testBadRankGroup() {
		final Card black1 = new Card(Color.BLACK, Rank.ONE);
		final Card blue1 = new Card(Color.BLUE, Rank.ONE);
		final Card red2 = new Card(Color.RED, Rank.TWO);
		final CardGroup group = new CardGroup(Arrays.asList(black1, blue1, red2));
		Assert.assertFalse("A group must have different colors", group.isValid());
	}

	/**
	 *
	 */
	@Test
	public void testValidGroup() {
		final Card black1 = new Card(Color.BLACK, Rank.ONE);
		final Card blue1 = new Card(Color.BLUE, Rank.ONE);
		final Card red1 = new Card(Color.RED, Rank.ONE);
		final Card yellow1 = new Card(Color.YELLOW, Rank.ONE);
		final CardGroup group = new CardGroup(Arrays.asList(black1, blue1, red1, yellow1));
		Assert.assertTrue("Should be valid", group.isValid());
	}

	/**
	 *
	 */
	@Test
	public void testValidGroupJoker() {
		final Card black1 = new Card(Color.BLACK, Rank.ONE);
		final Card blue1 = new Card(Color.BLUE, Rank.ONE);
		final Card red1 = new Card(Color.RED, Rank.ONE);
		final CardGroup group = new CardGroup(Arrays.asList(joker, black1, blue1, red1));
		Assert.assertTrue("Should be valid", group.isValid());
	}

	/**
	 *
	 */
	@Test
	public void testValidRun() {
		final Card black1 = new Card(Color.BLACK, Rank.ONE);
		final Card black2 = new Card(Color.BLACK, Rank.TWO);
		final Card black3 = new Card(Color.BLACK, Rank.THREE);
		final CardGroup group = new CardGroup(Arrays.asList(black1, black2, black3));
		Assert.assertTrue("Should be valid", group.isValid());
	}

	/**
	 *
	 */
	@Test
	public void testRunOverflow() {
		final Card black12 = new Card(Color.BLACK, Rank.TWELVE);
		final Card black13 = new Card(Color.BLACK, Rank.THIRTEEN);
		final Card black1 = new Card(Color.BLACK, Rank.ONE);
		final CardGroup group = new CardGroup(Arrays.asList(black12, black13, black1));
		Assert.assertFalse("Should be valid", group.isValid());
	}

	/**
	 *
	 */
	@Test
	public void testTooManyInGroup() {
		final Card black1 = new Card(Color.BLACK, Rank.ONE);
		final Card blue1 = new Card(Color.BLUE, Rank.ONE);
		final Card red1 = new Card(Color.RED, Rank.ONE);
		final Card yellow1 = new Card(Color.YELLOW, Rank.ONE);
		final CardGroup group = new CardGroup(Arrays.asList(black1, blue1, red1, yellow1, joker));
		Assert.assertFalse("A group can have at most 4 cards", group.isValid());
	}

}