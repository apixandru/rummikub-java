package com.github.apixandru.games.rummikub.model2;

import com.github.apixandru.games.rummikub.model.Card;
import com.github.apixandru.games.rummikub.model.CardGroup;
import com.github.apixandru.games.rummikub.model.util.Util;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.github.apixandru.games.rummikub.model.Constants.NUM_COLS;
import static com.github.apixandru.games.rummikub.model.Constants.NUM_ROWS;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 25, 2015
 */
final class Board {

    private final UndoManager undoManager;

    final Card[][] cards;
    private final Optional<BoardCallback> callback;

    /**
     * @param undoManager
     * @param callback
     */
    Board(final UndoManager undoManager, final BoardCallback callback) {
        this.undoManager = undoManager;
        this.callback = Optional.ofNullable(callback);
        this.cards = new Card[NUM_ROWS][NUM_COLS];
    }

    /**
     * @param x
     * @param y
     * @return
     */
    private boolean inBounds(final int x, final int y) {
        return y < NUM_ROWS && x < NUM_COLS;
    }

    /**
     * @param x
     * @param y
     * @return
     */
    private boolean isFree(int x, int y) {
        return null == cards[y][x];
    }


    /**
     * @param card
     * @param x
     * @param y
     * @return
     */
    boolean placeCard(Card card, int x, int y) {
        if (inBounds(x, y) && isFree(x, y) || card == cards[y][x]) {
            cards[y][x] = card;
            this.callback.ifPresent(rummikubCallback -> rummikubCallback.cardPlacedOnBoard(card, x, y));
            return true;
        }
        return false;
    }

    /**
     * @param x
     * @param y
     * @return
     */
    Card removeCard(final int x, final int y) {
        final Card card = cards[y][x];
        cards[y][x] = null;
        return card;
    }

    /**
     * @param fromX
     * @param fromY
     * @param toX
     * @param toY
     */
    void moveCard(final int fromX, final int fromY, final int toX, final int toY) {
        final Card card = removeCard(fromX, fromY);
        placeCard(card, toX, toY);
    }

    /**
     * @return
     */
    public boolean isValid() {
        return streamGroups().allMatch(CardGroup::isValid);
    }

    /**
     * @return
     */
    private Stream<CardGroup> streamGroups() {
        return Arrays.stream(cards)
                .map(Util::splitNonEmptyGroups)
                .flatMap(List::stream)
                .map(CardGroup::new);
    }

}
