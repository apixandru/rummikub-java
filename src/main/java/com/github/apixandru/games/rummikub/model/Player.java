package com.github.apixandru.games.rummikub.model;

import static com.github.apixandru.games.rummikub.model.Constants.NUM_COLS;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 22, 2015
 */
public class Player extends Grid {

    /**
     * @param game
     */
    Player(final RummikubGame game) {
        super(game, 3, NUM_COLS);
    }

}
