package com.apixandru.games.rummikub.brotocol.game.server;

import com.apixandru.games.rummikub.brotocol.Header;
import com.apixandru.games.rummikub.brotocol.Packet;
import com.apixandru.rummikub.api.Card;

import static com.apixandru.games.rummikub.brotocol.Brotocol.SERVER_RECEIVED_CARD;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 13, 2016
 */
@Header(SERVER_RECEIVED_CARD)
public class PacketReceiveCard implements Packet {

    public Card card;

    public Integer hint;

}
