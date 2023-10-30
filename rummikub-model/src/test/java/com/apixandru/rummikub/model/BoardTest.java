package com.apixandru.rummikub.model;

import com.apixandru.rummikub.api.BoardListener;
import com.apixandru.rummikub.api.Card;
import org.junit.jupiter.api.Test;

import static com.apixandru.rummikub.api.Color.BLUE;
import static com.apixandru.rummikub.api.Color.RED;
import static com.apixandru.rummikub.api.Rank.ONE;
import static com.apixandru.rummikub.model.TestUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verifyNoInteractions;

public class BoardTest {

    private final BoardImpl board = new BoardImpl();

    @Test
    public void should_not_be_able_to_place_cards_out_of_bounds() {
        assertFalse(board.placeCard(BLACK_ONE_1, 0, 7));
        assertFalse(board.placeCard(BLACK_ONE_1, 20, 0));
    }

    @Test
    public void should_not_be_able_to_place_different_cards_on_the_same_place() {
        assertTrue(board.placeCard(BLACK_ONE_1, 0, 6));
        assertFalse(board.placeCard(BLACK_ONE_2, 0, 6));
    }

    @Test
    public void should_not_be_able_to_place_the_same_cards_in_the_same_slot() {
        assertTrue(board.placeCard(BLACK_ONE_1, 0, 6));
        assertFalse(board.placeCard(BLACK_ONE_1, 0, 6));
    }

    @Test
    public void should_mark_a_slot_as_taken_after_placing_a_card_there() {
        board.placeCard(BLACK_ONE_1, 0, 6);
        assertFalse(board.isFree(0, 6));
    }

    @Test
    public void should_recognize_valid_formations() {
        board.placeCard(BLACK_ONE_1, 0, 1);
        board.placeCard(card(RED, ONE), 1, 1);
        board.placeCard(card(BLUE, ONE), 2, 1);
        assertTrue(board.isValid());
    }

    @Test
    public void should_be_able_to_remove_the_card_and_place_another_one_in_its_position() {
        board.placeCard(BLACK_ONE_1, 0, 6);
        board.removeCard(0, 6);
        assertTrue(board.isFree(0, 6));
    }

    @Test
    public void should_take_the_exact_card_that_was_placed_in_the_slot() {
        board.placeCard(BLACK_ONE_1, 3, 5);
        Card removedCard = board.removeCard(3, 5);

        assertEquals(BLACK_ONE_1, removedCard);
        assertNotEquals(BLACK_ONE_2, removedCard);
    }

    @Test
    public void should_not_move_card_over_a_card_that_already_exists() {
        board.placeCard(BLACK_ONE_1, 3, 5);
        board.placeCard(BLACK_ONE_2, 4, 5);

        board.moveCard(4, 5, 3, 5);
        assertFalse(board.isFree(4, 5));
    }

    @Test
    public void should_not_send_message_after_the_board_listener_was_removed() {
        BoardListener listener = mock(BoardListener.class);
        board.addBoardListener(listener);
        board.removeBoardListener(listener);

        board.placeCard(BLACK_ONE_1, 3, 5);

        verifyNoInteractions(listener);
    }

    @Test
    public void should_remove_all_cards() {
        board.placeCard(BLACK_ONE_1, 3, 5);
        board.removeAllCards();
        assertTrue(board.isFree(3, 5));
    }

    @Test
    public void should_have_position_B_taken_after_a_card_was_moved_from_position_A_to_position_B() {
        board.placeCard(BLACK_ONE_1, 0, 6);
        board.moveCard(0, 6, 1, 5);

        assertTrue(board.isFree(0, 6));
        assertFalse(board.isFree(1, 5));
    }

    @Test
    public void should_recognize_that_a_gap_between_cards_invalidates_the_formation() {
        board.placeCard(BLACK_ONE_1, 16, 6);
        board.placeCard(card(RED, ONE), 18, 6);
        board.placeCard(card(BLUE, ONE), 19, 6);

        assertFalse(board.isValid());
        assertEquals(2, board.streamGroups().count());
    }

}
