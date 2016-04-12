package com.apixandru.games.rummikub.model;

import com.apixandru.games.rummikub.api.BoardCallback;
import com.apixandru.games.rummikub.api.Card;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static com.apixandru.games.rummikub.api.Constants.NUM_COLS;
import static com.apixandru.games.rummikub.api.Constants.NUM_ROWS;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 25, 2015
 */
final class Board {

    private static final Logger log = LoggerFactory.getLogger(Board.class);

    final Card[][] cards;

    private final List<BoardCallback> boardCallbacks = new ArrayList<>();

    Board() {
        this.cards = new Card[NUM_ROWS][NUM_COLS];
    }

    private static boolean inBounds(final int x, final int y) {
        return y < NUM_ROWS && x < NUM_COLS;
    }

    boolean isFree(int x, int y) {
        return null == cards[y][x];
    }


    boolean placeCard(Card card, int x, int y) {
        if (!inBounds(x, y)) {
            return false;
        }
        if (!isFree(x, y) && card != cards[y][x]) {
            return false;
        }
        cards[y][x] = card;
        for (final BoardCallback callback : boardCallbacks) {
            callback.onCardPlacedOnBoard(card, x, y);
        }
        return true;
    }

    private Card removeCard(final int x, final int y, final boolean unlock) {
        final Card card = cards[y][x];
        for (final BoardCallback boardCallback : boardCallbacks) {
            boardCallback.onCardRemovedFromBoard(card, x, y, unlock);
        }
        cards[y][x] = null;
        return card;
    }

    Card removeCard(final int x, final int y) {
        return removeCard(x, y, false);
    }

    void moveCard(final int fromX, final int fromY, final int toX, final int toY) {
        final Card card = removeCard(fromX, fromY);
        if (!placeCard(card, toX, toY)) {
            final boolean inBounds = inBounds(toX, toY);
            final boolean free = isFree(toX, toY);
            log.error("Cannot place {} on board. inBounds={}, free={}, cards[{}][{}]={}", card, inBounds, free, toX, toY, cards[toY][toX]);
            placeCard(card, fromX, fromY);
        }
    }

    boolean isValid() {
        return streamGroups().allMatch(CardGroup::isValid);
    }

    Stream<CardGroup> streamGroups() {
        return Arrays.stream(cards)
                .map(Util::splitNonEmptyGroups)
                .flatMap(List::stream)
                .map(CardGroup::new);
    }

    List<Card> removeAllCards() {
        final List<Card> cards = new ArrayList<>();
        for (int y = 0; y < NUM_ROWS; y++) {
            for (int x = 0; x < NUM_COLS; x++) {
                if (!isFree(x, y)) {
                    cards.add(removeCard(x, y, true));
                }
            }
        }
        return cards;
    }

    void addBoardListener(final BoardCallback boardCallback) {
        if (null != boardCallback) {
            this.boardCallbacks.add(boardCallback);
        }
    }

    void removeBoardListener(final BoardCallback boardCallback) {
        this.boardCallbacks.remove(boardCallback);
    }

}
