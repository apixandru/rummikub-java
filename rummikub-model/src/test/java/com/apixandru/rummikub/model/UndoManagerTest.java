package com.apixandru.rummikub.model;

import com.apixandru.rummikub.api.Card;
import org.junit.jupiter.api.Test;

import static com.apixandru.rummikub.model.TestUtils.BLACK_ONE_1;
import static org.mockito.Mockito.*;

public class UndoManagerTest {

    private final UndoManager undoManager = new UndoManager();

    @Test
    public void should_place_card_back_on_board() {
        EnhancedPlayer player = mock(EnhancedPlayer.class);
        Board board = mock(Board.class);

        when(board.getCards())
                .thenReturn(new Card[0][0]);

        undoManager.trackBoardToPlayer(BLACK_ONE_1, 1, 1);
        undoManager.undo(player, board);

        verify(board, times(1))
                .placeCard(BLACK_ONE_1, 1, 1);
    }

}
