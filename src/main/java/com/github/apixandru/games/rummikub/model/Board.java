/**
 *
 */
package com.github.apixandru.games.rummikub.model;

import com.github.apixandru.games.rummikub.model.util.Util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Alexandru-Constantin Bledea
 * @since Sep 21, 2015
 */
public final class Board {

    private final Card[][] cardsOnBoard = new Card[7][20];

    public boolean placeCard(Card card, int x, int y) {
        if (inBounds(x,y)) {
            // more checks
            cardsOnBoard[y][x] = card;
            return true;
        }
        return false;
    }

    private boolean inBounds(final int x, final int y) {
        return true; // todo
    }

    public List<CardGroup> getGroups() {
        return streamGroups(cardsOnBoard).collect(Collectors.toList());
    }

    public boolean isValid() {
        return streamGroups(cardsOnBoard).allMatch(CardGroup::isValid);
    }

    private Stream<CardGroup> streamGroups(Card[][] cardsOnBoard) {
        return Arrays.stream(cardsOnBoard)
                .map(Util::splitNonEmptyGroups)
                .flatMap(List::stream)
                .map(CardGroup::new);
    }

}
