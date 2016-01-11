package com.apixandru.games.rummikub.model;

import com.apixandru.games.rummikub.api.Card;
import com.apixandru.games.rummikub.api.Player;
import com.apixandru.games.rummikub.api.PlayerCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 25, 2015
 */
final class PlayerImpl implements Player<Integer> {

    private final PlayerListener listener;
    private final String name;

    final Optional<PlayerCallback<Integer>> callback;

    final List<Card> cards = new ArrayList<>();

    /**
     * @param name
     * @param listener
     * @param callback
     */
    PlayerImpl(final String name, final PlayerListener listener, final PlayerCallback<Integer> callback) {
        this.listener = listener;
        this.name = name;
        this.callback = Optional.ofNullable(callback);
    }

    @Override
    public void placeCardOnBoard(final Card card, final int x, final int y) {
        this.listener.placeCardOnBoard(this, card, x, y);
    }

    @Override
    public void moveCardOnBoard(final Card card, final int fromX, final int fromY, final int toX, final int toY) {
        this.listener.moveCardOnBoard(this, card, fromX, fromY, toX, toY);
    }

    @Override
    public void takeCardFromBoard(final Card card, final int x, final int y, final Integer hint) {
        this.listener.takeCardFromBoard(this, card, x, y, hint);
    }

    @Override
    public void endTurn() {
        this.listener.requestEndTurn(this);
    }

    /**
     * @param card
     * @param hint
     */
    void receiveCard(final Card card, final Integer hint) {
        this.cards.add(card);
        this.callback.ifPresent(callback -> callback.cardReceived(card, hint));
    }

    /**
     * @param card
     */
    void receiveCard(final Card card) {
        receiveCard(card, null);
    }

    /**
     * @param card
     */
    void removeCard(final Card card) {
        this.cards.remove(card);
    }

    @Override
    public String getName() {
        return this.name;
    }

}
