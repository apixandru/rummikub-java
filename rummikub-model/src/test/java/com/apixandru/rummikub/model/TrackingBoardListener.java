package com.apixandru.rummikub.model;

import com.apixandru.rummikub.api.game.BoardListener;
import com.apixandru.rummikub.api.game.Card;
import com.apixandru.rummikub.model.support.BoardAction;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 03, 2016
 */
final class TrackingBoardListener implements BoardListener {

    private final List<BoardAction> actions = new ArrayList<>();

    @Override
    public void onCardPlacedOnBoard(final Card card, final int x, final int y) {
        this.actions.add(new BoardAction(BoardAction.Action.ADDED, card, x, y));
    }

    @Override
    public void onCardRemovedFromBoard(final Card card, final int x, final int y, boolean reset) {
        this.actions.add(new BoardAction(BoardAction.Action.REMOVED, card, x, y));
    }

    List<BoardAction> getActions() {
        return Collections.unmodifiableList(this.actions);
    }

    void clear() {
        this.actions.clear();
    }

}
