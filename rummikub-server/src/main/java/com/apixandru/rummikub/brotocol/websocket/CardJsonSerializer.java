package com.apixandru.rummikub.brotocol.websocket;

import com.apixandru.rummikub.api.Card;
import com.apixandru.rummikub.api.Constants;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

final class CardJsonSerializer implements JsonSerializer<Card>, JsonDeserializer<Card> {

    @Override
    public Card deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
        int cardIndex = json.getAsInt();
        if (cardIndex == -1) {
            return null;
        }
        return Constants.CARDS.get(cardIndex);
    }

    @Override
    public JsonElement serialize(Card card, Type typeOfSrc, JsonSerializationContext context) {
        return context.serialize(Constants.CARDS.indexOf(card));
    }

}
