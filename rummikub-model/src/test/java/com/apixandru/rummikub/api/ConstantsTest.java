package com.apixandru.rummikub.api;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConstantsTest {

    @Test
    public void testNumberOfCards() {
        assertEquals(Constants.NUM_CARDS, Constants.CARDS.size());
    }

}
