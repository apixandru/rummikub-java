package com.apixandru.rummikub.brotocol.websocket;

import com.apixandru.rummikub.api.Card;
import com.apixandru.rummikub.api.Constants;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

final class CardTypeAdapter extends TypeAdapter<Card> {

    @Override
    public void write(JsonWriter out, Card value) throws IOException {
        if (value == null) {
            out.nullValue();
            return;
        }
        out.value(Constants.CARDS.indexOf(value));
    }

    @Override
    public Card read(JsonReader in) throws IOException {
        long index = in.nextLong();
        if (index == -1) {
            return null;
        }
        return Constants.CARDS.get((int) index);
    }

}
