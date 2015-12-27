/**
 *
 */
package com.github.apixandru.games.rummikub.model;

import com.github.apixandru.games.rummikub.model.util.Util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.github.apixandru.games.rummikub.model.Constants.NUM_COLS;
import static com.github.apixandru.games.rummikub.model.Constants.NUM_ROWS;

/**
 * @author Alexandru-Constantin Bledea
 * @since Sep 21, 2015
 */
public final class Board extends Grid {

    /**
     *
     */
    Board() {
        super(NUM_ROWS, NUM_COLS);
    }

    public List<CardGroup> getGroups() {
        return streamGroups().collect(Collectors.toList());
    }

    public boolean isValid() {
        return streamGroups().allMatch(CardGroup::isValid);
    }

    private Stream<CardGroup> streamGroups() {
        return Arrays.stream(cards)
                .map(Util::splitNonEmptyGroups)
                .flatMap(List::stream)
                .map(CardGroup::new);
    }

}
