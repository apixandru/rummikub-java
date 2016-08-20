package com.apixandru.rummikub.connection.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.util.stream.Stream;

/**
 * @author Alexandru-Constantin Bledea
 * @since Aug 20, 2016
 */
public final class Util {

    private static final Logger log = LoggerFactory.getLogger(Util.class);

    private Util() {
    }

    public static void closeQuietly(Closeable closeable) {
        try {
            closeable.close();
        } catch (IOException e) {
            log.error("Failed to close connection", e);
        }
    }

    public static void closeQuietly(Closeable... closeable) {
        Stream.of(closeable)
                .forEach(Util::closeQuietly);
    }

}
