package com.apixandru.rummikub.api

import spock.lang.Specification

import static Rank.*

/**
 * @author Alexandru-Constantin Bledea
 * @since Oct 23, 2016
 */
class RankTest extends Specification {

    def "validate numbers"() {
        expect:
        " 1" == ONE.toString()
        " 2" == TWO.toString()
        " 3" == THREE.toString()
        " 4" == FOUR.toString()
        " 5" == FIVE.toString()
        " 6" == SIX.toString()
        " 7" == SEVEN.toString()
        " 8" == EIGHT.toString()
        " 9" == NINE.toString()
        "10" == TEN.toString()
        "11" == ELEVEN.toString()
        "12" == TWELVE.toString()
        "13" == THIRTEEN.toString()
    }

}
