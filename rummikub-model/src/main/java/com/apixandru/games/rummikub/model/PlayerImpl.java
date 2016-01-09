package com.apixandru.games.rummikub.model;

import com.apixandru.games.rummikub.api.BoardCallback;
import com.apixandru.games.rummikub.api.Card;
import com.apixandru.games.rummikub.api.GameEventListener;
import com.apixandru.games.rummikub.api.Player;
import com.apixandru.games.rummikub.api.PlayerCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Alexandru-Constantin Bledea
 * @since December 25, 2015
 */
final class PlayerImpl<H> implements Player<H>, BoardCallback, GameEventListener {

    private final PlayerListener listener;
    private final String name;

    final Optional<PlayerCallback<H>> callback;

    final List<Card> cards = new ArrayList<>();

    /**
     * @param name
     * @param listener
     * @param callback
     */
    PlayerImpl(final String name, final PlayerListener listener, final PlayerCallback<H> callback) {
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
    public void takeCardFromBoard(final Card card, final int x, final int y, final H hint) {
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
    public void receiveCard(final Card card, final H hint) {
        this.cards.add(card);
        this.callback.ifPresent(callback -> callback.cardReceived(card, hint));
    }

    /**
     * @param card
     */
    public void receiveCard(final Card card) {
        receiveCard(card, null);
    }

    /**
     * @param card
     */
    public void removeCard(final Card card) {
        this.cards.remove(card);
    }

    @Override
    public void onCardPlacedOnBoard(final Card card, final int x, final int y) {
        this.callback.ifPresent(callback -> callback.onCardPlacedOnBoard(card, x, y));
    }

    @Override
    public void onCardRemovedFromBoard(final Card card, final int x, final int y) {
        this.callback.ifPresent(callback -> callback.onCardRemovedFromBoard(card, x, y));
    }

    /**
     * @param mine
     */
    @Override
    public void newTurn(final boolean mine) {
        this.callback.ifPresent(callback -> callback.newTurn(mine));
    }

    @Override
    public void gameOver(final String player, final boolean quit, final boolean me) {
        this.callback.ifPresent(callback -> callback.gameOver(player, quit, me));
    }

    @Override
    public String getName() {
        return this.name;
    }

}
