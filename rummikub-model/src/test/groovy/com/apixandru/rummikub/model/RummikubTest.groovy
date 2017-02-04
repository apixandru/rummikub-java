package com.apixandru.rummikub.model

import com.apixandru.rummikub.api.game.Card
import com.apixandru.rummikub.api.game.Player
import com.apixandru.rummikub.api.game.PlayerCallback
import spock.lang.Specification

import static com.apixandru.rummikub.model.ImplementationDetails.cloneBoard
import static com.apixandru.rummikub.model.ImplementationDetails.cloneCards
import static com.apixandru.rummikub.model.ImplementationDetails.countCards
import static com.apixandru.rummikub.model.ImplementationDetails.currentPlayer
import static com.apixandru.rummikub.model.ImplementationDetails.endTurnUntilValidGroup
import static com.apixandru.rummikub.model.ImplementationDetails.getCards
import static com.apixandru.rummikub.model.ImplementationDetails.getFirstCard
import static java.util.Arrays.deepEquals
import static org.junit.Assert.assertEquals
import static org.junit.Assert.assertSame

/**
 * @author Alexandru-Constantin Bledea
 * @since February 04, 2017
 */
class RummikubTest extends Specification {

    Rummikub<Integer> rummikub
    Player<Integer> player

    def setup() {
        this.rummikub = RummikubFactory.newInstance()
        this.player = addPlayer("Player 1")
    }

    def addPlayer(String playerName) {
        rummikub.addPlayer(playerName, Mock(PlayerCallback))
    }

    def placeCardsOnFirstSlots(final List<Card> cards) {
        for (int i = 0; i < cards.size(); i++) {
            this.player.placeCardOnBoard(cards.get(i), i, 0)
        }
    }

    def countCards() {
        countCards(this.player)
    }

    def cardsOnBoard() {
        cloneBoard(this.rummikub)
    }

    def getFirstCard() {
        getFirstCard(this.player)
    }

    def endTurnUntilValidGroup() {
        endTurnUntilValidGroup(this.player)
    }

    def assertCardsOnBoard(final Card[][] cards) {
        deepEquals(cards, cardsOnBoard())
    }

    def "should only accept end turn requests from the current player"() {
        given:
        final Player player2 = addPlayer("Johnny")

        expect:
        assertSame(player, currentPlayer(rummikub))

        player2.endTurn()
        assertSame(player, currentPlayer(rummikub))

        player.endTurn()
        assertSame(player2, currentPlayer(rummikub))

        player2.endTurn()
        assertSame(player, currentPlayer(rummikub))
    }

    def "should give new players 14 cards"() {
        given:
        final Player player2 = addPlayer("Johnny")

        expect:
        assertEquals(14, countCards(player))
        assertEquals(14, countCards(player2))
    }

    def "should give current player a card when the turn ends if player just ends the turn"() {
        given:
        final int numberOfCardsBeforeEndTurn = countCards()

        when:
        player.endTurn()

        then:
        assertEquals(numberOfCardsBeforeEndTurn + 1, countCards())
    }

    def "should not give a card if new cards were placed on the board"() {
        given:
        final List<Card> group = endTurnUntilValidGroup()
        placeCardsOnFirstSlots(group)
        final List<Card> cardsBeforeEndTurn = cloneCards(player)

        when:
        player.endTurn()

        then:
        assertEquals(cardsBeforeEndTurn, getCards(player))
    }

    def "should have less cards in hand after the card was placed on the board"() {
        setup:
        final Card card = getFirstCard()
        final Card[][] cards = cardsOnBoard()

        when:
        player.placeCardOnBoard(card, 0, 0)

        then:
        !deepEquals(cardsOnBoard(), cards)
    }

    def "should rollback if the turn ends and the board is not in an invalid state"() {
        setup:
        final Card card = getFirstCard()
        final Card[][] cards = cardsOnBoard()

        when:
        player.placeCardOnBoard(card, 0, 0)
        player.endTurn()

        then:
        assertCardsOnBoard(cards)
    }

    def "should be able to take the cards that the player just placed on the board back if the turn didn't end"() {
        setup:
        final Card card = getFirstCard()
        final Card[][] cards = cardsOnBoard()

        when:
        player.placeCardOnBoard(card, 0, 0)
        player.takeCardFromBoard(card, 0, 0, null)

        then:
        assertCardsOnBoard(cards)
    }

    def "should not be able to take cards from the board that weren't placed in this turn"() {
        given:
        def group = endTurnUntilValidGroup()
        placeCardsOnFirstSlots(group)
        player.endTurn()
        def cards = cardsOnBoard()

        when:
        player.takeCardFromBoard(group.get(0), 0, 0, null)

        then:
        deepEquals(cardsOnBoard(), cards)
    }

    def "should have one less card after it was placed on the board"() {
        given:
        def numCardsBeforePlace = countCards()
        def card = getFirstCard()

        when:
        player.placeCardOnBoard(card, 0, 0)

        then:
        assertEquals(numCardsBeforePlace - 1, countCards())
    }

    def "should rollback changes and give card to the player if the cards were just shuffled on the board"() {
        given:
        def group = endTurnUntilValidGroup()
        placeCardsOnFirstSlots(group)
        player.endTurn()

        def cardsOnBoard = cardsOnBoard()
        def cardsInHand = countCards()

        when:
        player.moveCardOnBoard(0, 0, group.size(), 0)
        player.endTurn()

        then:
        assertCardsOnBoard(cardsOnBoard)
        assertEquals(cardsInHand + 1, countCards())
    }

    def "should not be allowed to place cards out of bounds"() {
        given:
        def cardsOnBoard = cardsOnBoard()
        def card = getFirstCard()

        when:
        player.placeCardOnBoard(card, 0, 7)

        then:
        assertCardsOnBoard(cardsOnBoard)
    }

}
