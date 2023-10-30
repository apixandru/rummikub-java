package com.apixandru.rummikub.api;

import org.junit.jupiter.api.Test;

import static com.apixandru.rummikub.api.Color.RED;
import static com.apixandru.rummikub.api.Rank.EIGHT;
import static org.junit.jupiter.api.Assertions.*;

public class CardTest {

    @Test
    public void cannotCreateCardWithRankButNoColor() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Card(null, EIGHT);
        });
    }

    @Test
    public void cannotCreateCardWithColorButNoRank() {
        assertThrows(IllegalArgumentException.class, () -> {
            new Card(RED, null);
        });
    }

    @Test
    public void createValidCard() {
        Card card = new Card(RED, EIGHT);

        assertSame(RED, card.getColor());
        assertSame(EIGHT, card.getRank());
        assertEquals("RED  8", card.toString());
    }

    @Test
    public void createJoker() {
        Card card = new Card(null, null);

        assertSame(null, card.getColor());
        assertSame(null, card.getRank());
        assertEquals("JOKER", card.toString());
    }

}
