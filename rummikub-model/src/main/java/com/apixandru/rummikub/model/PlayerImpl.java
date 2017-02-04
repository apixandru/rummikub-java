package com.apixandru.rummikub.model;

import com.apixandru.rummikub.api.game.Card;
import com.apixandru.rummikub.api.game.Player;
import com.apixandru.rummikub.api.game.PlayerCallback;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 25, 2015
 */
final class PlayerImpl implements Player<Integer> {

    final List<Card> cards = new ArrayList<>();

    private final PlayerListener listener;
    private final String name;
    private final PlayerCallback<Integer> callback;

    PlayerImpl(final String name, final PlayerListener listener, final PlayerCallback<Integer> callback) {
        this.listener = listener;
        this.name = name;
        this.callback = callback;
    }

    @Override
    public void placeCardOnBoard(final Card card, final int x, final int y) {
        this.listener.placeCardOnBoard(this, card, x, y);
    }

    @Override
    public void moveCardOnBoard(final int fromX, final int fromY, final int toX, final int toY) {
        this.listener.moveCardOnBoard(this, fromX, fromY, toX, toY);
    }

    @Override
    public void takeCardFromBoard(final Card card, final int x, final int y, final Integer hint) {
        this.listener.takeCardFromBoard(this, card, x, y, hint);
    }

    @Override
    public void endTurn() {
        this.listener.requestEndTurn(this);
    }

    void receiveCard(final Card card, final Integer hint) {
        this.cards.add(card);
        this.callback.cardReceived(card, hint);
    }

    void receiveCard(final Card card) {
        receiveCard(card, null);
    }

    void removeCard(final Card card) {
        this.cards.remove(card);
    }

    @Override
    public String getName() {
        return this.name;
    }

}
