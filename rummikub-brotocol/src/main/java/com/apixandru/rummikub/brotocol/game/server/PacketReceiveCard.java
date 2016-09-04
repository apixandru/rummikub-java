package com.apixandru.rummikub.brotocol.game.server;

import com.apixandru.rummikub.api.game.Card;
import com.apixandru.rummikub.brotocol.Header;
import com.apixandru.rummikub.brotocol.Packet;

import static com.apixandru.rummikub.brotocol.Brotocol.SERVER_RECEIVED_CARD;

/**
 * @author Alexandru-Constantin Bledea
 * @since January 13, 2016
 */
@Header(SERVER_RECEIVED_CARD)
public class PacketReceiveCard implements Packet {

    public Card card;

    public Integer hint;

}
