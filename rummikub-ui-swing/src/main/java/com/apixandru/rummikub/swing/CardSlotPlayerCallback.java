package com.apixandru.rummikub.swing;

import com.apixandru.rummikub.api.Card;
import com.apixandru.rummikub.api.PlayerCallback;

/**
 * @author Alexandru-Constantin Bledea
 * @since Apr 21, 2016
 */
class CardSlotPlayerCallback implements PlayerCallback<Integer> {

    private final PlayerCallback<CardSlot> cardSlotPlayerCallback;
    private final CardSlotIndexConverter converter;

    CardSlotPlayerCallback(final PlayerCallback<CardSlot> cardSlotPlayerCallback, final CardSlotIndexConverter converter) {
        this.cardSlotPlayerCallback = cardSlotPlayerCallback;
        this.converter = converter;
    }

    @Override
    public void cardReceived(final Card card, final Integer hint) {
        cardSlotPlayerCallback.cardReceived(card, converter.getCardSlot(hint));
    }

}
