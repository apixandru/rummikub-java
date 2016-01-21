package com.apixandru.games.rummikub.swing;

import com.apixandru.games.rummikub.api.BoardCallback;
import com.apixandru.games.rummikub.api.Card;
import com.apixandru.games.rummikub.api.GameEventListener;
import com.apixandru.games.rummikub.api.GameOverReason;
import com.apixandru.games.rummikub.client.ConnectionListener;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 04, 2016
 */
final class GameListener implements BoardCallback, GameEventListener, MoveHelper, ConnectionListener {

    private final List<Card> cardsLockedOnBoard = new ArrayList<>();
    private final List<Card> cardsJustPlacedOnBoard = new ArrayList<>();

    private final JGridPanel board;
    private final JButton btnEndTurn;

    private final AtomicBoolean myTurn = new AtomicBoolean();
    private final JFrame frame;

    /**
     * @param frame
     * @param board
     * @param btnEndTurn
     */
    GameListener(final JFrame frame, final JGridPanel board, final JButton btnEndTurn) {
        this.frame = frame;
        this.board = board;
        this.btnEndTurn = btnEndTurn;
        newTurn(false);
    }

    @Override
    public void onCardPlacedOnBoard(final Card card, final int x, final int y) {
        UiUtil.placeCard(card, board.slots[y][x]);
        this.cardsJustPlacedOnBoard.add(card);
    }

    @Override
    public void onCardRemovedFromBoard(final Card card, final int x, final int y, final boolean reset) {
        UiUtil.removeCard(card, board.slots[y][x]);
        this.cardsJustPlacedOnBoard.remove(card);
        if (reset) {
            this.cardsLockedOnBoard.remove(card);
        }
    }

    @Override
    public void newTurn(final boolean myTurn) {
        this.btnEndTurn.setEnabled(myTurn);
        this.myTurn.set(myTurn);
        this.cardsLockedOnBoard.addAll(this.cardsJustPlacedOnBoard);
        this.cardsJustPlacedOnBoard.clear();
    }

    @Override
    public boolean canInteractWithBoard() {
        return this.myTurn.get();
    }

    @Override
    public boolean canTakeCardFromBoard(final Card card) {
        return canInteractWithBoard() && !this.cardsLockedOnBoard.contains(card);
    }

    @Override
    public void gameOver(final String player, final GameOverReason reason, final boolean me) {
        String message;
        int icon;
        switch (reason) {
            case PLAYER_QUIT:
                message = player + " quit the game.";
                icon = JOptionPane.ERROR_MESSAGE;
                break;
            case GAME_WON:
                if (me) {
                    message = "You won!";
                    icon = JOptionPane.INFORMATION_MESSAGE;
                } else {
                    message = player + " won.";
                    icon = JOptionPane.INFORMATION_MESSAGE;
                }
                break;
            case NO_MORE_CARDS:
                message = "No more cards left!";
                icon = JOptionPane.INFORMATION_MESSAGE;
                break;
            default:
                throw new IllegalArgumentException(String.valueOf(reason));
        }
        JOptionPane.showMessageDialog(frame, message, "Game Over", icon);
        frame.dispose();
    }

    @Override
    public void onConnectionLost() {
        JOptionPane.showMessageDialog(frame,
                "You have been disconnected from the server. Press OK to exit.",
                "Disconnected",
                JOptionPane.ERROR_MESSAGE);
        frame.dispose();
    }

}
