/**
 *
 */
package com.github.apixandru.games.rummikub;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

/**
 * @author Alexandru-Constantin Bledea
 * @since Sep 20, 2015
 */
public final class CardPile {

	private final Queue<Card> cards;

	{
		final LinkedList<Card> cards = new LinkedList<Card>();

		for (final Rank rank : Rank.values()) {
			for (final Color color : Color.values()) {
				cards.add(new Card(color, rank));
			}
		}

		// jokers
		cards.add(new Card(null, null));
		cards.add(new Card(null, null));

		Collections.shuffle(cards);

		this.cards = cards;
	}

	/**
	 * @return
	 */
	public int getNumberOfCards() {
		return this.cards.size();
	}

	/**
	 * @return
	 */
	public boolean hasMoreCards() {
		return !this.cards.isEmpty();
	}

	/**
	 * @return
	 */
	public Card nextCard() {
		return this.cards.remove();
	}

}
