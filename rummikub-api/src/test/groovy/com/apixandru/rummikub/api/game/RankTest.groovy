package com.apixandru.rummikub.api.game

import spock.lang.Specification

import static com.apixandru.rummikub.api.game.Rank.EIGHT
import static com.apixandru.rummikub.api.game.Rank.ELEVEN
import static com.apixandru.rummikub.api.game.Rank.FIVE
import static com.apixandru.rummikub.api.game.Rank.FOUR
import static com.apixandru.rummikub.api.game.Rank.NINE
import static com.apixandru.rummikub.api.game.Rank.ONE
import static com.apixandru.rummikub.api.game.Rank.SEVEN
import static com.apixandru.rummikub.api.game.Rank.SIX
import static com.apixandru.rummikub.api.game.Rank.TEN
import static com.apixandru.rummikub.api.game.Rank.THIRTEEN
import static com.apixandru.rummikub.api.game.Rank.THREE
import static com.apixandru.rummikub.api.game.Rank.TWELVE
import static com.apixandru.rummikub.api.game.Rank.TWO

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
