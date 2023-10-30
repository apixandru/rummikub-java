package com.apixandru.rummikub.api;


import org.junit.jupiter.api.Test;

import static com.apixandru.rummikub.api.Color.*;
import static org.junit.jupiter.api.Assertions.assertSame;

public class ColorTest {

    @Test
    public void validateColorsToString() {
        assertSame("YLW", YELLOW.toString());
        assertSame("BLK", BLACK.toString());
        assertSame("BLU", BLUE.toString());
        assertSame("RED", RED.toString());
    }

}
