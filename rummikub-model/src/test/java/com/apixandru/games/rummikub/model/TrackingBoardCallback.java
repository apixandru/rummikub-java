package com.apixandru.games.rummikub.model;

import com.apixandru.games.rummikub.api.BoardCallback;
import com.apixandru.games.rummikub.api.Card;
import com.apixandru.games.rummikub.model.support.BoardAction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 03, 2016
 */
public final class TrackingBoardCallback implements BoardCallback {

    private final List<BoardAction> actions = new ArrayList<>();

    @Override
    public void onCardPlacedOnBoard(final Card card, final int x, final int y) {
        this.actions.add(new BoardAction(BoardAction.Action.ADDED, card, x, y));
    }

    @Override
    public void onCardRemovedFromBoard(final Card card, final int x, final int y) {
        this.actions.add(new BoardAction(BoardAction.Action.REMOVED, card, x, y));
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
