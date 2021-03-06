package com.apixandru.rummikub.model

import com.apixandru.rummikub.api.BoardListener
import spock.lang.Specification

import static com.apixandru.rummikub.api.Color.BLUE
import static com.apixandru.rummikub.api.Color.RED
import static com.apixandru.rummikub.api.Rank.ONE
import static com.apixandru.rummikub.model.TestUtils.BLACK_ONE_1
import static com.apixandru.rummikub.model.TestUtils.BLACK_ONE_2
import static com.apixandru.rummikub.model.TestUtils.card

/**
 * @author Alexandru-Constantin Bledea
 * @since Oct 23, 2016
 */
class BoardTest extends Specification {

    BoardImpl board

    def "should not be able to place cards out of bounds"() {
        expect:
        !board.placeCard(BLACK_ONE_1, 0, 7)
        !board.placeCard(BLACK_ONE_1, 20, 0)
    }

    def "should not be able to place different cards on the same place"() {
        expect:
        board.placeCard(BLACK_ONE_1, 0, 6)
        !board.placeCard(BLACK_ONE_2, 0, 6)
    }

    def "should not be able to place the same cards in the same slot"() {
        expect:
        board.placeCard(BLACK_ONE_1, 0, 6)
        !board.placeCard(BLACK_ONE_1, 0, 6)
    }

    def "should mark a slot as taken after placing a card there"() {
        when:
        board.placeCard(BLACK_ONE_1, 0, 6)

        then:
        !board.isFree(0, 6)
    }

    def "should recognize valid formations"() {
        when:
        board.placeCard(BLACK_ONE_1, 0, 1)
        board.placeCard(card(RED, ONE), 1, 1)
        board.placeCard(card(BLUE, ONE), 2, 1)

        then:
        board.isValid()
    }

    def "should be able to remove the card and place another one in its position"() {
        given:
        board.placeCard(BLACK_ONE_1, 0, 6)

        when:
        board.removeCard(0, 6)

        then:
        board.isFree(0, 6)
    }

    def "should take the exact card that was placed in the slot"() {
        given:
        board.placeCard(BLACK_ONE_1, 3, 5)

        when:
        def removedCard = board.removeCard(3, 5)

        then:
        BLACK_ONE_1 == removedCard
        BLACK_ONE_2 != removedCard
    }

    def "should not move card over a card that already exists"() {
        given:
        board.placeCard(BLACK_ONE_1, 3, 5)
        board.placeCard(BLACK_ONE_2, 4, 5)

        when:
        board.moveCard(4, 5, 3, 5)

        then:
        !board.isFree(4, 5)
    }

    def "should not send message after the board listener was removed"() {
        given:
        def listener = Mock(BoardListener)
        board.addBoardListener(listener)
        board.removeBoardListener(listener)

        when:
        board.placeCard(BLACK_ONE_1, 3, 5)

        then:
        0 * listener.onCardPlacedOnBoard(_, _, _)
    }

    def "should remove all cards"() {
        given:
        board.placeCard(BLACK_ONE_1, 3, 5)

        when:
        board.removeAllCards()

        then:
        board.isFree(3, 5)
    }

    def "should have position B taken after a card was moved from position A to position B"() {
        given:
        board.placeCard(BLACK_ONE_1, 0, 6)

        when:
        board.moveCard(0, 6, 1, 5)

        then:
        board.isFree(0, 6)
        !board.isFree(1, 5)
    }

    def "should recognize that a gap between cards invalidates the formation"() {
        given:
        board.placeCard(BLACK_ONE_1, 16, 6)
        board.placeCard(card(RED, ONE), 18, 6)
        board.placeCard(card(BLUE, ONE), 19, 6)

        expect:
        !board.isValid()
        2 == board.streamGroups().count()
    }

    def setup() {
        board = new BoardImpl()
    }

}
