/**
 *
 */
package com.github.apixandru.games.rummikub;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Alexandru-Constantin Bledea
 * @since Sep 16, 2015
 */
public final class CardGroup {

	private final List<Card> cards;

	CardGroup(final List<Card> cards) {
		this.cards = Collections.unmodifiableList(new ArrayList<>(cards));
	}

	/**
	 * @return
	 */
	public boolean isValid() {
		if (size() < 3) {
			return false;
		}
		return isValidGroup();
	}

	/**
	 * A valid group cannot have more than one color repeating
	 *
	 * @return
	 */
	private boolean isValidGroup() {
		if (size() > 4) {
			return false;
		}
		if (!Cards.isDifferentColors(cards)) {
			return false;
		}
		return Cards.isSameRanks(cards);
	}

	/**
	 * @return
	 */
	private int size() {
		return cards.size();
	}

}
