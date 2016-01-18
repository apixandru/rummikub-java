package com.apixandru.games.rummikub.brotocol;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 13, 2016
 */
@Target(TYPE)
@Retention(RUNTIME)
public @interface Header {

    /**
     * @return
     */
    Brotocol value();

}
