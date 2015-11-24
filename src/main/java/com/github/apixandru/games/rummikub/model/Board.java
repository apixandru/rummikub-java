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
public class Board {

    private final Card[][] cardsOnBoard = new Card[7][20];

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
