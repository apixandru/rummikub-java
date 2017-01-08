package com.apixandru.rummikub.api.game

import spock.lang.Specification

import static com.apixandru.rummikub.api.game.Color.RED
import static com.apixandru.rummikub.api.game.Rank.EIGHT

/**
 * @author Alexandru-Constantin Bledea
 * @since Oct 23, 2016
 */
class CardTest extends Specification {

    def "should not be able to create card with no color but with rank"() {
        when:
        new Card(null, EIGHT)

        then:
        thrown(IllegalArgumentException)
    }

    def "should not be able to create card with no rank but with color"() {
        when:
        new Card(RED, null)

        then:
        thrown(IllegalArgumentException)
    }

    def "should create valid card"() {
        given:
        def card = new Card(RED, EIGHT)

        expect:
        RED == card.getColor()
        EIGHT == card.getRank()
        "RED  8" == card.toString()
    }

    def "should create joker"() {
        given:
        def card = new Card(null, null)

        expect:
        null == card.getColor()
        null == card.getRank()
        "JOKER" == card.toString()
    }

}
