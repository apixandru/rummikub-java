package com.apixandru.rummikub.api

import spock.lang.Specification

/**
 * @author Alexandru-Constantin Bledea
 * @since Oct 23, 2016
 */
class ConstantsTest extends Specification {

    def "should pass test coverage"() {
        expect:
        new Constants()
    }

    def "expect correct number of cards"() {
        expect:
        Constants.NUM_CARDS == Constants.CARDS.size()
    }

}
