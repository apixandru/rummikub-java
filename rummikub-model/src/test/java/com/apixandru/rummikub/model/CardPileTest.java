package com.apixandru.rummikub.model;

import com.apixandru.rummikub.api.Constants;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Alexandru-Constantin Bledea
 * @since Sep 20, 2015
 */
@SuppressWarnings("static-method")
public class CardPileTest {

    @Test
    public void testNumberOfCards() {
        final CardPile cardPile = new CardPile();
        assertSame(Constants.NUM_CARDS, cardPile.getNumberOfCards(), "Numbers of cards don't match");
    }

    @Test
    public void testHasCards() {
        final CardPile cardPile = new CardPile();
        while (cardPile.hasMoreCards()) {
            assertNotNull(cardPile.nextCard(), "Should have cards");
        }
    }

    @Test
    public void testExhaustedPile() {
        final CardPile cardPile = new CardPile();
        while (cardPile.hasMoreCards()) {
            assertNotNull(cardPile.nextCard(), "Should have cards");
        }
        assertThrows(NoSuchElementException.class, () -> {
            cardPile.nextCard();
        });
    }

}
