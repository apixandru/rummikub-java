package com.github.apixandru.games.rummikub.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 23, 2015
 */
public class RummikubGame {

    private final Board board;
    private final List<Player> players = new ArrayList<>();

    private final List<Grid> grids = new ArrayList<>();

    /**
     *
     */
    public RummikubGame() {
        this.board = new Board(this); // shouldn't leak this reference...
        this.grids.add(board);
    }

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
     * @return
     */
    public Board getBoard() {
        return board;
    }

    /**
     * @param card
     */
    public void removeCard(final Card card) {
        grids.stream()
                .forEach((grid) -> grid.removeCard(card));
    }

}
