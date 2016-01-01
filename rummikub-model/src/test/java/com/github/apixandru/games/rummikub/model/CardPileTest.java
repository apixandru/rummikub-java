/**
 *
 */
package com.github.apixandru.games.rummikub.model;

import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;

/**
 * @author Alexandru-Constantin Bledea
 * @since Sep 20, 2015
 */
@SuppressWarnings("static-method")
public class CardPileTest {

    /**
     * @throws Exception
     */
    @Test
    public void testNumberOfCards() throws Exception {
        final CardPile cardPile = new CardPile();
        assertSame("Numbers of cards don't match", 106, cardPile.getNumberOfCards());
    }

    /**
     * @throws Exception
     */
    @Test
    public void testHasCards() throws Exception {
        final CardPile cardPile = new CardPile();
        while (cardPile.hasMoreCards()) {
            assertNotNull("Should have cards", cardPile.nextCard());
        }
    }

    /**
     * @throws Exception
     */
    @Test(expected = NoSuchElementException.class)
    public void testExhaustedPile() throws Exception {
        final CardPile cardPile = new CardPile();
        while (cardPile.hasMoreCards()) {
            assertNotNull("Should have cards", cardPile.nextCard());
        }
        cardPile.nextCard();
    }

}