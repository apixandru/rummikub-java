package com.apixandru.rummikub.api.game;

import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.unmodifiableList;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 20, 2015
 */
public final class Constants {

    public static final int NUM_ROWS = 7;
    public static final int NUM_COLS = 20;
    public static final int NUM_CARDS = 106;

    public static final List<Card> CARDS;

    static {
        final List<Card> cards = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            for (final Rank rank : Rank.values()) {
                for (final Color color : Color.values()) {
                    cards.add(new Card(color, rank));
                }
            }
            // joker
            cards.add(new Card(null, null));
        }
        CARDS = unmodifiableList(cards);
    }

    private Constants() {
    }

}
