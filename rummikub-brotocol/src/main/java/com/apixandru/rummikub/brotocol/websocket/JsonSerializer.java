package com.apixandru.rummikub.brotocol.websocket;

import com.apixandru.rummikub.api.Card;
import com.apixandru.rummikub.brotocol.Packet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;

import javax.websocket.Decoder;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import static com.apixandru.rummikub.brotocol.websocket.PacketConstants.getPacketClass;
import static com.apixandru.rummikub.brotocol.websocket.PacketConstants.getPacketCode;
import static java.lang.Integer.parseInt;
import static org.slf4j.LoggerFactory.getLogger;

public final class JsonSerializer implements Encoder.Text<Packet>, Decoder.Text<Packet> {

    private static final Logger log = getLogger(JsonSerializer.class);

    private final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Card.class, new CardJsonSerializer())
            .create();

    private static String[] split(String message) {
        return message.split(" ", 2);
    }

    private static Class<?> extractClass(String[] parts) {
        int classCode = parseInt(parts[0]);
        return getPacketClass(classCode);
    }

    private static boolean isPacketClass(Class<?> deserializedClass) {
        return deserializedClass != null
                && Packet.class.isAssignableFrom(deserializedClass);
    }

    @Override
    public Packet decode(String message) {
        String[] parts = split(message);
        Class<?> packetClass = extractClass(parts);
        String content = parts[1];
        return (Packet) gson.fromJson(content, packetClass);
    }

    @Override
    public boolean willDecode(String message) {
        String[] parts = split(message);
        return parts.length == 2
                && isPacketClass(extractClass(parts));
    }

    @Override
    public String encode(Packet packet) {
        int packetCode = getPacketCode(packet);
        return packetCode + " " + gson.toJson(packet);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }

}
