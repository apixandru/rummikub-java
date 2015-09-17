/**
 *
 */
package com.github.apixandru.games.rummikub;

import java.util.Collection;
import java.util.HashSet;

/**
 * @author Alexandru-Constantin Bledea
 * @since Sep 16, 2015
 */
final class Cards {

	/**
	 *
	 */
	private Cards() {
	}

	/**
	 * @return
	 */
	static boolean isDifferentColors(final Collection<Card> cards) {
		final Collection<Color> colors = new HashSet<>();
		for (final Card card : cards) {
			final Color cardColor = card.getColor();
			if (null == cardColor) {
				continue;
			}
			if (colors.contains(cardColor)) {
				return false;
			}
			colors.add(cardColor);
		}
		return true;
	}

	/**
	 * @param cards
	 * @return
	 */
	static boolean isSameRanks(final Collection<Card> cards) {
		final Rank rank = getFirstRank(cards);
		for (final Card card : cards) {
			final Rank cardRank = card.getRank();
			if (null != cardRank && rank != cardRank) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @param cards
	 * @return
	 */
	public static boolean isAscendingRanks(final Collection<Card> cards) {
		Rank expected = null;
		boolean first = true;
		for (final Card card : cards) {
			final Rank rank = card.getRank();
			if (first) {
				expected = rank;
				first = false;
			}
//			if rank is null then joker, matches
			if (rank != null && expected != rank) {
				return false;
			}
			expected = Rank.next(expected);
		}
		return true;
	}


	/**
	 * @param cards
	 * @return
	 */
	private static Rank getFirstRank(final Collection<Card> cards) {
		for (final Card card : cards) {
			final Rank rank = card.getRank();
			if (null != rank) {
				return rank;
			}
		}
		return null;
	}

}
