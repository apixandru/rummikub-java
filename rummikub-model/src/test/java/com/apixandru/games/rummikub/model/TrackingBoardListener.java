package com.apixandru.games.rummikub.model;

import com.apixandru.games.rummikub.model.support.BoardAction;
import com.apixandru.rummikub.api.BoardListener;
import com.apixandru.rummikub.api.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 03, 2016
 */
public final class TrackingBoardListener implements BoardListener {

    private final List<BoardAction> actions = new ArrayList<>();

    @Override
    public void onCardPlacedOnBoard(final Card card, final int x, final int y) {
        this.actions.add(new BoardAction(BoardAction.Action.ADDED, card, x, y));
    }

    @Override
    public void onCardRemovedFromBoard(final Card card, final int x, final int y, boolean reset) {
        this.actions.add(new BoardAction(BoardAction.Action.REMOVED, card, x, y));
    }

    public List<BoardAction> getActions() {
        return Collections.unmodifiableList(this.actions);
    }

    public void clear() {
        this.actions.clear();
    }

}
