package com.apixandru.rummikub.model;

import com.apixandru.rummikub.api.Color;
import com.apixandru.rummikub.api.Rank;
import org.junit.jupiter.api.Test;

import static com.apixandru.rummikub.model.TestUtils.card;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Alexandru-Constantin Bledea
 * @since Sep 16, 2015
 */
@SuppressWarnings({"unused", "static-method"})
public final class CreateCardTest {

    @Test
    public void testNoColorRank() {
        assertThrows(IllegalArgumentException.class, () -> {
            card(null, Rank.EIGHT);
        });
    }

    @Test
    public void testColorNoRank() {
        assertThrows(IllegalArgumentException.class, () -> {
            card(Color.BLACK, null);
        });
    }

    @Test
    public void testColorRank() {
        card(Color.BLACK, Rank.ONE);
    }

    @Test
    public void testNoColorNoRank() {
        card(null, null);
    }

}
