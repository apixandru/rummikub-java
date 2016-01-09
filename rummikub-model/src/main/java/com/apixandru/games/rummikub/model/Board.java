package com.apixandru.games.rummikub.model;

import com.apixandru.games.rummikub.api.BoardCallback;
import com.apixandru.games.rummikub.api.Card;
import com.apixandru.games.rummikub.model.util.Util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static com.apixandru.games.rummikub.api.Constants.NUM_COLS;
import static com.apixandru.games.rummikub.api.Constants.NUM_ROWS;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 25, 2015
 */
final class Board {

    final Card[][] cards;
    private final Optional<BoardCallback> callback;

    /**
     * @param callback
     */
    Board(final BoardCallback callback) {
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
    boolean isFree(int x, int y) {
        return null == cards[y][x];
    }


    /**
     * @param card
     * @param x
     * @param y
     * @return
     */
    boolean placeCard(Card card, int x, int y) {
        if (!inBounds(x, y)) {
            return false;
        }
        if (!isFree(x, y) && card != cards[y][x]) {
            return false;
        }
        cards[y][x] = card;
        this.callback.ifPresent(callback -> callback.onCardPlacedOnBoard(card, x, y));
        return true;
    }

    /**
     * @param x
     * @param y
     * @return
     */
    Card removeCard(final int x, final int y, final boolean unlock) {
        final Card card = cards[y][x];
        this.callback.ifPresent(callback -> callback.onCardRemovedFromBoard(card, x, y, unlock));
        cards[y][x] = null;
        return card;
    }

    /**
     * @param x
     * @param y
     * @return
     */
    Card removeCard(final int x, final int y) {
        return removeCard(x, y, false);
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
    Stream<CardGroup> streamGroups() {
        return Arrays.stream(cards)
                .map(Util::splitNonEmptyGroups)
                .flatMap(List::stream)
                .map(CardGroup::new);
    }

    /**
     * @return
     */
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

}
