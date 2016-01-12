package com.apixandru.games.rummikub.client;

import com.apixandru.games.rummikub.api.BoardCallback;
import com.apixandru.games.rummikub.api.Card;
import com.apixandru.games.rummikub.api.GameEventListener;
import com.apixandru.games.rummikub.api.PlayerCallback;
import com.apixandru.games.rummikub.brotocol.BroReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.EOFException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.apixandru.games.rummikub.brotocol.Brotocol.SERVER_CARD_PLACED;
import static com.apixandru.games.rummikub.brotocol.Brotocol.SERVER_CARD_REMOVED;
import static com.apixandru.games.rummikub.brotocol.Brotocol.SERVER_GAME_OVER;
import static com.apixandru.games.rummikub.brotocol.Brotocol.SERVER_NEW_TURN;
import static com.apixandru.games.rummikub.brotocol.Brotocol.SERVER_RECEIVED_CARD;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 04, 2016
 */
final class PlayerCallbackAdapter<H> implements Runnable {

    private final Logger log = LoggerFactory.getLogger(PlayerCallbackAdapter.class);

    private final BroReader reader;

    private final PlayerCallback<H> playerCallback;
    private final BoardCallback boardCallback;
    private final GameEventListener gameEventListener;

    private final List<Card> cards;
    private final List<H> hints;
    private final ConnectionListener connectionListener;

    /**
     * @param connector the connector
     * @param reader    the reader
     * @param cards     the list of all the cards in the game
     */
    PlayerCallbackAdapter(final RummikubConnector<H> connector,
                          final BroReader reader,
                          final List<Card> cards) {
        this.reader = reader;

        this.playerCallback = connector.callback;
        this.boardCallback = connector.boardCallback;
        this.gameEventListener = connector.gameEventListener;

        this.connectionListener = connector.connectionListener;

        this.cards = Collections.unmodifiableList(new ArrayList<>(cards));
        this.hints = Collections.unmodifiableList(new ArrayList<>(connector.hints));
    }

    @Override
    public void run() {
        try (final BroReader reader = this.reader) {
            while (true) {
                final int input = reader.readInt();
                switch (input) {
                    case SERVER_CARD_PLACED:
                        handleCardPlaced(getCard());
                        break;
                    case SERVER_CARD_REMOVED:
                        handleCardRemoved(getCard());
                        break;
                    case SERVER_RECEIVED_CARD:
                        handleReceivedCard(getCard());
                        break;
                    case SERVER_NEW_TURN:
                        handleNewTurn();
                        break;
                    case SERVER_GAME_OVER:
                        handleGameOver();
                        break;
                    default:
                        throw new IllegalArgumentException("Unknown input: " + input);
                }
            }
        } catch (final EOFException e) {
            log.debug("Server was shutdown?", e);
        } catch (final IOException e) {
            log.error("Unknown exception", e);
        }
        this.connectionListener.onDisconnected();
    }

    /**
     * @return the card identified by the index
     * @throws IOException
     */
    private Card getCard() throws IOException {
        return this.cards.get(reader.readInt());
    }

    /**
     * @throws IOException
     */
    private void handleNewTurn() throws IOException {
        final boolean myTurn = reader.readBoolean();
        log.debug("Received newTurn(myTurn={})", myTurn);
        this.gameEventListener.newTurn(myTurn);
    }

    /**
     * @param card the card
     * @throws IOException
     */
    private void handleReceivedCard(final Card card) throws IOException {
        final int hintIndex = reader.readInt();
        log.debug("Received cardReceived(card={}, hintIndex={})", card, hintIndex);
        this.playerCallback.cardReceived(card, -1 == hintIndex ? null : this.hints.get(hintIndex));
    }

    /**
     * @param card the card
     * @throws IOException
     */
    private void handleCardRemoved(final Card card) throws IOException {
        final int x = reader.readInt();
        final int y = reader.readInt();
        final boolean unlock = reader.readBoolean();
        log.debug("Received onCardRemovedFromBoard(card={}, x={}, y={}, unlock={})", card, x, y, unlock);
        this.boardCallback.onCardRemovedFromBoard(card, x, y, unlock);
    }

    /**
     * @param card the card
     * @throws IOException
     */
    private void handleCardPlaced(final Card card) throws IOException {
        final int x = reader.readInt();
        final int y = reader.readInt();
        log.debug("Received onCardPlacedOnBoard(card={}, x={}, y={})", card, x, y);
        this.boardCallback.onCardPlacedOnBoard(card, x, y);
    }

    /**
     *
     */
    private void handleGameOver() throws IOException {
        final String player = reader.readString();
        final boolean quit = reader.readBoolean();
        final boolean me = reader.readBoolean();
        log.debug("Received gameOver(player={}, quit={}, me={})", player, quit, me);
        this.gameEventListener.gameOver(player, quit, me);
    }

}
