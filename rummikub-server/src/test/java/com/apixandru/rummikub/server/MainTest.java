package com.apixandru.rummikub.server;

import org.assertj.core.api.AbstractIntegerAssert;
import org.junit.Test;

import static com.apixandru.rummikub.server.Main.DEFAULT_PORT;
import static com.apixandru.rummikub.server.Main.getPort;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Alexandru-Constantin Bledea
 * @since Aug 14, 2016
 */
public class MainTest {

    private static AbstractIntegerAssert<?> assertPortWhenArgs(String... args) {
        return assertThat(getPort(args));
    }

    @Test
    public void testGetPort() {
        assertPortWhenArgs()
                .isEqualTo(DEFAULT_PORT);

        assertPortWhenArgs("")
                .isEqualTo(DEFAULT_PORT);

        assertPortWhenArgs("ifs")
                .isEqualTo(DEFAULT_PORT);

        assertPortWhenArgs("231")
                .isEqualTo(231);

        assertPortWhenArgs("42", "13")
                .isEqualTo(42);
    }

}
