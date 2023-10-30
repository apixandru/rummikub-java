package com.apixandru.rummikub.brotocol.websocket;

import com.apixandru.rummikub.api.Card;
import com.apixandru.rummikub.brotocol.Packet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.websocket.Decoder;
import jakarta.websocket.Encoder;
import jakarta.websocket.EndpointConfig;
import org.slf4j.Logger;

import static org.slf4j.LoggerFactory.getLogger;

public final class JsonSerializer implements Encoder.Text<Packet>, Decoder.Text<Packet> {

    private static final Logger log = getLogger(JsonSerializer.class);

    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Packet.class, new PacketJsonSerializer())
            .registerTypeAdapter(Card.class, new CardJsonSerializer())
            .create();

    @Override
    public Packet decode(String message) {
        log.debug("Decoding {}", message);
        return gson.fromJson(message, Packet.class);
    }

    @Override
    public boolean willDecode(String message) {
        return true;
    }

    @Override
    public String encode(Packet packet) {
        String encoded = gson.toJson(packet, Packet.class);
        log.debug("Encoded {}", encoded);
        return encoded;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {
    }

    @Override
    public void destroy() {

    }

}
