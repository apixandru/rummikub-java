/**
 *
 */
package com.github.apixandru.games.rummikub;

import org.junit.Test;

/**
 * @author Alexandru-Constantin Bledea
 * @since Sep 16, 2015
 */
@SuppressWarnings ({"unused", "static-method"})
public final class CreateCardTest {

	@Test (expected = IllegalArgumentException.class)
	public void testNoColorRank() {
		new Card(null, Rank.EIGHT);
	}

	@Test (expected = IllegalArgumentException.class)
	public void testColorNoRank() {
		new Card(Color.BLACK, null);
	}

	@Test
	public void testColorRank() {
		new Card(Color.BLACK, Rank.ONE);
	}

	@Test
	public void testNoColorNoRank() {
		new Card(null, null);
	}

}
