package com.github.apixandru.games.rummikub.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 23, 2015
 */
public class RummikubGame {

    private final List<Grid> grids = new ArrayList<>();

    /**
     * @param card
     */
    public void removeCard(final Card card) {
        grids.stream()
                .forEach((grid) -> grid.removeCard(card));
    }

}
