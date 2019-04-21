package com.apixandru.rummikub.api

import spock.lang.Specification

import static com.apixandru.rummikub.api.GameOverReason.*

/**
 * @author Alexandru-Constantin Bledea
 * @since February 19, 2017
 */
class GameOverReasonTest extends Specification {

    def "should pass test coverage"() {
        expect:
        GameOverReason.values() == [PLAYER_QUIT, GAME_WON, NO_MORE_CARDS]
    }

}
