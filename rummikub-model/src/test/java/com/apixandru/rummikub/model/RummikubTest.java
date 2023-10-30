package com.apixandru.rummikub.model;

import com.apixandru.rummikub.api.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.apixandru.rummikub.api.GameOverReason.NO_MORE_CARDS;
import static com.apixandru.rummikub.api.GameOverReason.PLAYER_QUIT;
import static com.apixandru.rummikub.model.ImplementationDetails.*;
import static com.apixandru.rummikub.model.ImplementationDetails.getCards;
import static java.util.Arrays.deepEquals;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RummikubTest {

    private final Rummikub<Integer> rummikub = RummikubFactory.newInstance();
    private final Player<Integer> player = addPlayerAndSetNextPlayer("Player 1");

    public Player<Integer> addPlayer(String playerName) {
        return rummikub.addPlayer(playerName, mock(PlayerCallback.class));
    }

    public Player<Integer> addPlayerAndSetNextPlayer(String playerName) {
        Player<Integer> player = addPlayer(playerName);
        rummikub.setNextPlayer();
        return player;
    }

    public void placeCardsOnFirstSlots(final List<Card> cards) {
        for (int i = 0; i < cards.size(); i++) {
            this.player.placeCardOnBoard(cards.get(i), i, 0);
        }
    }

    public int countCards() {
        return ImplementationDetails.countCards(this.player);
    }

    public Card[][] cardsOnBoard() {
        return cloneBoard(this.rummikub);
    }

    public Card getFirstCard() {
        return ImplementationDetails.getFirstCard(this.player);
    }

    public List<Card> endTurnUntilValidGroup() {
        return ImplementationDetails.endTurnUntilValidGroup(this.player);
    }

    public void assertCardsOnBoard(final Card[][] cards) {
        deepEquals(cards, cardsOnBoard());
    }

    @Test
    public void should_only_accept_end_turn_requests_from_the_current_player() {
        Player<Integer> player2 = addPlayer("Johnny");

        assertSame(player, currentPlayer(rummikub));

        player2.endTurn();
        assertSame(player, currentPlayer(rummikub));

        player.endTurn();
        assertSame(player2, currentPlayer(rummikub));

        player2.endTurn();
        assertSame(player, currentPlayer(rummikub));
    }

    @Test
    public void should_send_game_over_event_when_player_quits() {
        GameEventListener eventListener = mock(GameEventListener.class);
        rummikub.addGameEventListener(eventListener);

        rummikub.removePlayer(player);

        verify(eventListener, times(1))
                .gameOver("Player 1", PLAYER_QUIT);
    }

    @Test
    public void should_end_game_if_no_more_cards() {
        Player<Integer> player2 = addPlayer("Player 2");

        GameEventListener eventListener = mock(GameEventListener.class);
        rummikub.addGameEventListener(eventListener);

        for (int i = 0; i < 39; i++) {
            player.endTurn();
            player2.endTurn();
        }

        player.endTurn();

        verify(eventListener, times(39))
                .newTurn("Player 1");

        verify(eventListener, times(39))
                .newTurn("Player 2");

        verify(eventListener, times(1))
                .gameOver("Player 1", NO_MORE_CARDS);
    }

    @Test
    public void should_reject_end_turn_requests_from_players_which_arent_the_current_player() {
        Player<Integer> player2 = addPlayer("Player 2");
        Card card = ImplementationDetails.getFirstCard(player2);

        BoardListener boardListener = mock(BoardListener.class);
        rummikub.addBoardListener(boardListener);

        player2.placeCardOnBoard(card, 1, 1);

        verifyNoInteractions(boardListener);
    }

    @Test
    public void should_give_new_players_14_cards() {
        final Player player2 = addPlayer("Johnny");

        assertEquals(14, ImplementationDetails.countCards(player));
        assertEquals(14, ImplementationDetails.countCards(player2));
    }

    @Test
    public void should_give_current_player_a_card_when_the_turn_ends_if_player_just_ends_the_turn() {
        final int numberOfCardsBeforeEndTurn = countCards();

        player.endTurn();

        assertEquals(numberOfCardsBeforeEndTurn + 1, countCards());
    }

    @Test
    public void should_not_give_a_card_if_new_cards_were_placed_on_the_board() {
        final List<Card> group = endTurnUntilValidGroup();
        placeCardsOnFirstSlots(group);
        final List<Card> cardsBeforeEndTurn = cloneCards(player);

        player.endTurn();

        assertEquals(cardsBeforeEndTurn, getCards(player));
    }

    @Test
    public void should_have_less_cards_in_hand_after_the_card_was_placed_on_the_board() {
        final Card card = getFirstCard();
        final Card[][] cards = cardsOnBoard();

        player.placeCardOnBoard(card, 0, 0);

        assertFalse(deepEquals(cardsOnBoard(), cards));
    }

    @Test
    public void should_rollback_if_the_turn_ends_and_the_board_is_not_in_an_invalid_state() {
        final Card card = getFirstCard();
        final Card[][] cards = cardsOnBoard();

        player.placeCardOnBoard(card, 0, 0);
        player.endTurn();

        assertCardsOnBoard(cards);
    }

    @Test
    public void should_be_able_to_take_the_cards_that_the_player_just_placed_on_the_board_back_if_the_turn_didnt_end() {
        final Card card = getFirstCard();
        final Card[][] cards = cardsOnBoard();

        player.placeCardOnBoard(card, 0, 0);
        player.takeCardFromBoard(card, 0, 0, null);

        assertCardsOnBoard(cards);
    }

    @Test
    public void should_not_be_able_to_take_cards_from_the_board_that_werent_placed_in_this_turn() {
        List<Card> group = endTurnUntilValidGroup();
        placeCardsOnFirstSlots(group);
        player.endTurn();
        Card[][] cards = cardsOnBoard();

        player.takeCardFromBoard(group.get(0), 0, 0, null);

        assertTrue(deepEquals(cardsOnBoard(), cards));
    }

    @Test
    public void should_have_one_less_card_after_it_was_placed_on_the_board() {
        int numCardsBeforePlace = countCards();
        Card card = getFirstCard();

        player.placeCardOnBoard(card, 0, 0);

        assertEquals(numCardsBeforePlace - 1, countCards());
    }

    @Test
    public void should_rollback_changes_and_give_card_to_the_player_if_the_cards_were_just_shuffled_on_the_board() {
        List<Card> group = endTurnUntilValidGroup();
        placeCardsOnFirstSlots(group);
        player.endTurn();

        Card[][] cardsOnBoard = cardsOnBoard();
        int cardsInHand = countCards();

        player.moveCardOnBoard(0, 0, group.size(), 0);
        player.endTurn();

        assertCardsOnBoard(cardsOnBoard);
        assertEquals(cardsInHand + 1, countCards());
    }

    @Test
    public void should_not_be_allowed_to_place_cards_out_of_bounds() {
        Card[][] cardsOnBoard = cardsOnBoard();
        Card card = getFirstCard();

        player.placeCardOnBoard(card, 0, 7);

        assertCardsOnBoard(cardsOnBoard);
    }


}
