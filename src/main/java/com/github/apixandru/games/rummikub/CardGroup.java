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

}
