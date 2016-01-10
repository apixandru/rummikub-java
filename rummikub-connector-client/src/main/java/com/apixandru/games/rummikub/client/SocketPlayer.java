package com.apixandru.games.rummikub.client;

import com.apixandru.games.rummikub.api.Card;
import com.apixandru.games.rummikub.api.Player;
import com.apixandru.games.rummikub.brotocol.BroWriter;
import com.apixandru.games.rummikub.brotocol.util.AbstractIntWritable;

import java.util.List;

import static com.apixandru.games.rummikub.brotocol.Brotocol.CLIENT_END_TURN;
import static com.apixandru.games.rummikub.brotocol.Brotocol.CLIENT_MOVE_CARD;
import static com.apixandru.games.rummikub.brotocol.Brotocol.CLIENT_PLACE_CARD;
import static com.apixandru.games.rummikub.brotocol.Brotocol.CLIENT_TAKE_CARD;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 04, 2016
 */
final class SocketPlayer<H> extends AbstractIntWritable implements Player<H> {

    private final List<H> hints;
    private final String playerName;

    /**
     * @param playerName the player name
     * @param writer     the writer
     * @param cards      the list of all the cards in the game
     * @param hints      the client hints
     */
    SocketPlayer(final String playerName, final BroWriter writer, final List<Card> cards, final List<H> hints) {
        super(writer, cards);
        this.hints = hints;
        this.playerName = playerName;
    }

    @Override
    public String getName() {
        return this.playerName;
    }

    @Override
    public void placeCardOnBoard(final Card card, final int x, final int y) {
        writeAndFlush(CLIENT_PLACE_CARD, card, x, y);
    }

    @Override
    public void moveCardOnBoard(final Card card, final int fromX, final int fromY, final int toX, final int toY) {
        writeAndFlush(CLIENT_MOVE_CARD, card, fromX, fromY, toX, toY);
    }

    @Override
    public void takeCardFromBoard(final Card card, final int x, final int y, final H hint) {
        writeAndFlush(CLIENT_TAKE_CARD, card, x, y, this.hints.indexOf(hint));
    }

    @Override
    public void endTurn() {
        writeAndFlush(CLIENT_END_TURN);
    }

}
