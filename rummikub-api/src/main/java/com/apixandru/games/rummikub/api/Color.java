/**
 *
 */
package com.apixandru.games.rummikub.api;

/**
 * @author Alexandru-Constantin Bledea
 * @since Sep 16, 2015
 */
public enum Color {

    RED("RED"),
    BLUE("BLU"),
    BLACK("BLK"),
    YELLOW("YLW");

    private final String string;

    Color(final String string) {
        this.string = string;
    }

    @Override
    public String toString() {
        return string;
    }

}
