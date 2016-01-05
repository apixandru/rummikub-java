package com.github.apixandru.games.rummikub.api;

import com.github.apixandru.games.rummikub.api.support.BoardAction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.github.apixandru.games.rummikub.api.support.BoardAction.Action.ADDED;
import static com.github.apixandru.games.rummikub.api.support.BoardAction.Action.REMOVED;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 03, 2016
 */
public final class TrackingBoardCallback implements BoardCallback {

    private final List<BoardAction> actions = new ArrayList<>();

    @Override
    public void onCardPlacedOnBoard(final Card card, final int x, final int y) {
        this.actions.add(new BoardAction(ADDED, card, x, y));
    }

    @Override
    public void onCardRemovedFromBoard(final Card card, final int x, final int y) {
        this.actions.add(new BoardAction(REMOVED, card, x, y));
    }

    /**
     * @return
     */
    public List<BoardAction> getActions() {
        return Collections.unmodifiableList(this.actions);
    }

    /**
     *
     */
    public void clear() {
        this.actions.clear();
    }

}
