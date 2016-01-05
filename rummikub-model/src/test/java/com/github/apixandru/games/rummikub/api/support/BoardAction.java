package com.github.apixandru.games.rummikub.api.support;

import com.github.apixandru.games.rummikub.api.Card;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 03, 2016
 */
public final class BoardAction {

    public final Action action;
    public final Card card;
    public final int x;
    public final int y;

    public BoardAction(final Action action, final Card card, final int x, final int y) {
        this.action = action;
        this.card = card;
        this.x = x;
        this.y = y;
    }

    public enum Action {
        ADDED,
        REMOVED
    }

}
