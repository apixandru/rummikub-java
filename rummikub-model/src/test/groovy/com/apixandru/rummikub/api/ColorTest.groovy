package com.apixandru.rummikub.api

import spock.lang.Specification

import static Color.*

/**
 * @author Alexandru-Constantin Bledea
 * @since Oct 23, 2016
 */
class ColorTest extends Specification {

    def "validate colors to string"() {
        expect:
        "YLW" == YELLOW.toString()
        "BLK" == BLACK.toString()
        "BLU" == BLUE.toString()
        "RED" == RED.toString()
    }

}
