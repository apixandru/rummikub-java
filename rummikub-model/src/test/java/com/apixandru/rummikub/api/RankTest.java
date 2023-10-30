package com.apixandru.rummikub.api;

import org.junit.jupiter.api.Test;

import static com.apixandru.rummikub.api.Rank.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class RankTest {

    @Test
    public void validateToString() {
        assertEquals(" 1", ONE.toString());
        assertEquals(" 2", TWO.toString());
        assertEquals(" 3", THREE.toString());
        assertEquals(" 4", FOUR.toString());
        assertEquals(" 5", FIVE.toString());
        assertEquals(" 6", SIX.toString());
        assertEquals(" 7", SEVEN.toString());
        assertEquals(" 8", EIGHT.toString());
        assertEquals(" 9", NINE.toString());
        assertEquals("10", TEN.toString());
        assertEquals("11", ELEVEN.toString());
        assertEquals("12", TWELVE.toString());
        assertEquals("13", THIRTEEN.toString());
    }

}
