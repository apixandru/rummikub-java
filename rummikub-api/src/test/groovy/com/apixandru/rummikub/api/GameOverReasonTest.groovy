package com.apixandru.rummikub.api

import spock.lang.Specification

import static com.apixandru.rummikub.api.GameOverReason.GAME_WON
import static com.apixandru.rummikub.api.GameOverReason.NO_MORE_CARDS
import static com.apixandru.rummikub.api.GameOverReason.PLAYER_QUIT

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
