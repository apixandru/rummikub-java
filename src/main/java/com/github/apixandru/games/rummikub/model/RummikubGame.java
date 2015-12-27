package com.github.apixandru.games.rummikub.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 23, 2015
 */
public class RummikubGame {

    private final List<Player> players = new ArrayList<>();

    private final List<Grid> grids = new ArrayList<>();

    /**
     * @return
     */
    public Player addPlayer() {
        Player player = new Player(this);
        this.players.add(player);
        this.grids.add(player);
        return player;
    }

    /**
     * @param card
     */
    public void removeCard(final Card card) {
        grids.stream()
                .forEach((grid) -> grid.removeCard(card));
    }

}
