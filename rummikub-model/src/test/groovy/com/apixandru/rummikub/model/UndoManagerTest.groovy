package com.apixandru.rummikub.model

import spock.lang.Specification

import static com.apixandru.rummikub.model.TestUtils.BLACK_ONE_1

/**
 * @author Alexandru-Constantin Bledea
 * @since February 19, 2017
 */
class UndoManagerTest extends Specification {

    UndoManager undoManager;

    def "should place card back on board"() {
        given:
        def player = Mock(EnhancedPlayer)
        def board = Mock(Board)
        undoManager.trackBoardToPlayer(BLACK_ONE_1, 1, 1)

        when:
        undoManager.undo(player, board)

        then:
        board.getCards() >> []
        1 * board.placeCard(BLACK_ONE_1, 1, 1)
    }

    def setup() {
        undoManager = new UndoManager();
    }

}
