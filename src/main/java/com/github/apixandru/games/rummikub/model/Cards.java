/**
 *
 */
package com.github.apixandru.games.rummikub.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

/**
 * @author Alexandru-Constantin Bledea
 * @since Sep 16, 2015
 */
public final class Cards {

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
	 * @return
	 */
	static boolean isAllSameColor(final Collection<Card> cards) {
		final Color color = getFirstColor(cards);
		for (final Card card : cards) {
			final Color cardColor = card.getColor();
			if (null != cardColor && color != cardColor) {
				return false;
			}
		}
		return true;
	}

	/**
	 * @param cards
	 * @return
	 */
	private static Color getFirstColor(final Collection<Card> cards) {
		for (final Card card : cards) {
			final Color color = card.getColor();
			if (null != color) {
				return color;
			}
		}
		return null;
	}

	/**
	 * @param cards
	 * @return
	 */
	static boolean isAscendingRanks(final List<Card> cards) {
		Rank expected = null;
		boolean first = true;
		for (int i = 0, to = cards.size(); i < to; i++) {
			final Card card = cards.get(i);
			final Rank rank = card.getRank();
			if (first && null != rank) {
				if (!isValidRankInRun(rank, i)) {
					return false;
				}
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
	 * Because the run can start with one or two jokers, we need to infer the
	 * rank of the cards that they substitute. This means that the rank in a run
	 * can never be lower than the card number in that run
	 *
	 * @param rank the rank of the card
	 * @param cardNumberInRun the number of the card in the group
	 * @return true if the card rank is valid for the position in the run
	 */
	private static boolean isValidRankInRun(final Rank rank, final int cardNumberInRun) {
		return rank.ordinal() >= cardNumberInRun;
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

	/**
	 * @param card
	 * @return
	 */
	public static boolean isJoker(final Card card) {
		return card.getRank() == null && card.getColor() == null;
	}

}
