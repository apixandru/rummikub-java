package com.apixandru.rummikub.swing;

import com.apixandru.rummikub.api.BoardListener;
import com.apixandru.rummikub.api.Card;
import com.apixandru.rummikub.api.GameEventListener;
import com.apixandru.rummikub.api.GameOverReason;
import com.apixandru.rummikub.swing.shared.MoveHelper;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 04, 2016
 */
final class GameListener implements BoardListener, GameEventListener, MoveHelper {

    private final List<Card> cardsLockedOnBoard = new ArrayList<>();
    private final List<Card> cardsJustPlacedOnBoard = new ArrayList<>();

    private final JGridPanel board;
    private final JButton btnEndTurn;

    private final AtomicBoolean myTurn = new AtomicBoolean();
    private final JFrame frame;
    private final String playerName;

    GameListener(final JFrame frame, final JGridPanel board, final JButton btnEndTurn, final String playerName) {
        this.frame = frame;
        this.board = board;
        this.btnEndTurn = btnEndTurn;
        this.playerName = playerName;
        newTurn(null); // TODO something else to distinguish?
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
    public void newTurn(final String player) {
        boolean myTurn = Objects.equals(player, playerName);
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
    public void gameOver(final String player, final GameOverReason reason) {
        String message;
        int icon;
        switch (reason) {
            case PLAYER_QUIT:
                message = player + " quit the game.";
                icon = JOptionPane.ERROR_MESSAGE;
                break;
            case GAME_WON:
                message = player + " won.";
                icon = JOptionPane.INFORMATION_MESSAGE;
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

}
