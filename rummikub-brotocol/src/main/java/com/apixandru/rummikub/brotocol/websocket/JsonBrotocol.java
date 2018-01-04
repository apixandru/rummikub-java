package com.apixandru.rummikub.brotocol.websocket;

import com.apixandru.rummikub.brotocol.Packet;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.Decoder;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public final class JsonBrotocol implements Encoder.Text<Packet>, Decoder.Text<Packet> {

    private static final Logger log = LoggerFactory.getLogger(JsonBrotocol.class);

    private final Gson gson = new Gson();

    private static String[] split(String message) {
        return message.split(" ", 2);
    }

    private static Class<?> extractClass(String[] parts) {
        try {
            return Class.forName(parts[0]);
        } catch (ClassNotFoundException e) {
            log.debug("Attempted to deserialize bed class", e);
            return null;
        }
    }

    private static boolean isPacketClass(Class<?> deserializedClass) {
        return deserializedClass != null &&
                Packet.class.isAssignableFrom(deserializedClass);
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
        if (parts.length != 2) {
            return false;
        }
        Class<?> deserializedClass = extractClass(parts);
        return isPacketClass(deserializedClass);
    }

    @Override
    public String encode(Packet packet) {
        return packet.getClass().getName() + " " + gson.toJson(packet);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }

}