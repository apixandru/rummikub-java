/**
 *
 */
package com.github.apixandru.games.rummikub;

import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Alexandru-Constantin Bledea
 * @since Sep 17, 2015
 */
@SuppressWarnings ("static-method")
public final class CardsTest {

	/**
	 *
	 */
	@Test
	public void testIsAscendingRanks() {
		final List<Card> cards = Arrays.asList(new Card(Color.RED, Rank.ONE), new Card(Color.RED, Rank.TWO), new Card(Color.RED, Rank.THREE));
		Assert.assertTrue("Cards should be in ascending order", Cards.isAscendingRanks(cards));
	}

	/**
	 *
	 */
	@Test
	public void testIsAscendingRanksWithJoker() {
		final List<Card> cards = Arrays.asList(new Card(Color.RED, Rank.ONE), new Card(null, null), new Card(Color.RED, Rank.THREE));
		Assert.assertTrue("Cards should be in ascending order", Cards.isAscendingRanks(cards));
	}

	/**
	 *
	 */
	@Test
	public void testIsAscendingRanksWithJokerBad() {
		final List<Card> cards = Arrays.asList(new Card(Color.RED, Rank.ONE), new Card(null, null), new Card(Color.RED, Rank.FOUR));
		Assert.assertFalse("Cards should not be in ascending order", Cards.isAscendingRanks(cards));
	}

	/**
	 *
	 */
	@Test
	public void testIsAscendingRanksBad() {
		final List<Card> cards = Arrays.asList(new Card(Color.RED, Rank.TWELVE), new Card(Color.RED, Rank.THIRTEEN), new Card(Color.RED, Rank.ONE));
		Assert.assertFalse("Cards should not be in ascending order", Cards.isAscendingRanks(cards));
	}

	/**
	 *
	 */
	@Test
	public void testIsAscendingRanksDoubleJoker() {
		final List<Card> cards = Arrays.asList(new Card(null, null), new Card(null, null), new Card(Color.RED, Rank.THREE));
		Assert.assertTrue("Jokers should map to 1 and 2", Cards.isAscendingRanks(cards));
	}

	/**
	 *
	 */
	@Test
	public void testIsAscendingRanksDoubleJokerBad() {
		final List<Card> cards = Arrays.asList(new Card(null, null), new Card(null, null), new Card(Color.RED, Rank.ONE));
		Assert.assertFalse("Jokers should map to 1 and 2, third card cannot be 1", Cards.isAscendingRanks(cards));
	}

}
