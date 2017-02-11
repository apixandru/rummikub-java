package com.apixandru.rummikub.model

import spock.lang.Specification

/**
 * @author Alexandru-Constantin Bledea
 * @since February 19, 2017
 */
class RummikubFactoryTest extends Specification {

    def "should pass test coverage"() {
        expect:
        new RummikubFactory()
    }

}
