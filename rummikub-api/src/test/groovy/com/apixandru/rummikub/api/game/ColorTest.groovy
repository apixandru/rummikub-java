package com.apixandru.rummikub.api.game

import spock.lang.Specification

import static com.apixandru.rummikub.api.game.Color.BLACK
import static com.apixandru.rummikub.api.game.Color.BLUE
import static com.apixandru.rummikub.api.game.Color.RED
import static com.apixandru.rummikub.api.game.Color.YELLOW

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
