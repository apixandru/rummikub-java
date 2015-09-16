/**
 *
 */
package com.github.apixandru.games.rummikub;

/**
 * @author Alexandru-Constantin Bledea
 * @since Sep 16, 2015
 */
public enum Rank {

	ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10), ELEVEN(11), TWELVE(12), THIRTEEN(13);

	private final int num;

	/**
	 * @param num
	 */
	private Rank(final int num) {
		this.num = num;
	}

}
