package com.apixandru.games.rummikub.server;

import com.apixandru.games.rummikub.api.Card;
import com.apixandru.games.rummikub.api.PlayerCallback;
import com.apixandru.games.rummikub.brotocol.BroWriter;
import com.apixandru.games.rummikub.brotocol.util.AbstractIntWritable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.apixandru.games.rummikub.brotocol.Brotocol.SERVER_CARD_PLACED;
import static com.apixandru.games.rummikub.brotocol.Brotocol.SERVER_CARD_REMOVED;
import static com.apixandru.games.rummikub.brotocol.Brotocol.SERVER_GAME_OVER;
import static com.apixandru.games.rummikub.brotocol.Brotocol.SERVER_NEW_TURN;
import static com.apixandru.games.rummikub.brotocol.Brotocol.SERVER_RECEIVED_CARD;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 05, 2016
 */
final class ClientCallback extends AbstractIntWritable implements PlayerCallback<Integer> {

    private static final Logger log = LoggerFactory.getLogger(ClientCallback.class);

    private final String playerName;

    /**
     * @param playerName the player name
     * @param writer the writer
     * @param cards  the list of all the cards in the game
     */
    ClientCallback(final String playerName, final BroWriter writer, final List<Card> cards) {
        super(writer, cards);
        this.playerName = playerName;
    }

    @Override
    public void cardReceived(final Card card, final Integer hint) {
        log.debug("[{}] Sending cardReceived(card={}, hint={})", playerName, card, hint);
        writeAndFlush(SERVER_RECEIVED_CARD, card, hint == null ? -1 : hint);
    }

    @Override
    public void onCardPlacedOnBoard(final Card card, final int x, final int y) {
        log.debug("[{}] Sending onCardPlacedOnBoard(card={}, x={}, y={})", playerName, card, x, y);
        writeAndFlush(SERVER_CARD_PLACED, card, x, y);
    }

    @Override
    public void onCardRemovedFromBoard(final Card card, final int x, final int y, final boolean reset) {
        log.debug("[{}] Sending onCardRemovedFromBoard(card={}, x={}, y={}, reset={})", playerName, card, x, y, reset);
        write(SERVER_CARD_REMOVED, card, x, y);
        write(reset);
        flush();
    }

    @Override
    public void newTurn(final boolean myTurn) {
        log.debug("[{}] Sending newTurn(myTurn={})", playerName, myTurn);
        write(SERVER_NEW_TURN);
        write(myTurn);
        flush();
    }

    @Override
    public void gameOver(final String player, final boolean quit, final boolean me) {
        log.debug("[{}] Sending gameOver(player={}, quit={}, me={})", playerName, player, quit, me);
        write(SERVER_GAME_OVER);
        write(player);
        write(quit, me);
        flush();
    }

}
