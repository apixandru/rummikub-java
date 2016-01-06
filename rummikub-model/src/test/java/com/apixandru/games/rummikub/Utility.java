package com.apixandru.games.rummikub;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.SOURCE;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 28, 2015
 */
@Retention(SOURCE)
@Target(METHOD)
@interface Utility {
}
