package com.apixandru.rummikub.model;

import com.apixandru.rummikub.api.Constants;
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

    @Test
    public void testNumberOfCards() {
        final CardPile cardPile = new CardPile();
        assertSame("Numbers of cards don't match", Constants.NUM_CARDS, cardPile.getNumberOfCards());
    }

    @Test
    public void testHasCards() {
        final CardPile cardPile = new CardPile();
        while (cardPile.hasMoreCards()) {
            assertNotNull("Should have cards", cardPile.nextCard());
        }
    }

    @Test(expected = NoSuchElementException.class)
    public void testExhaustedPile() {
        final CardPile cardPile = new CardPile();
        while (cardPile.hasMoreCards()) {
            assertNotNull("Should have cards", cardPile.nextCard());
        }
        cardPile.nextCard();
    }

}
