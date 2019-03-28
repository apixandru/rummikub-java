package com.apixandru.rummikub.brotocol.websocket;

import com.apixandru.rummikub.brotocol.Packet;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;

import static com.apixandru.rummikub.brotocol.websocket.PacketConstants.getPacketClass;
import static com.apixandru.rummikub.brotocol.websocket.PacketConstants.getPacketType;

final class PacketJsonSerializer implements JsonSerializer<Packet>, JsonDeserializer<Packet> {

    @Override
    public Packet deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonElement element = jsonElement.getAsJsonObject()
                .remove("type");
        if (element == null) {
            throw new JsonParseException("Missing 'type' property!");
        }
        String actualType = element.getAsString();
        return jsonDeserializationContext.deserialize(jsonElement, getPacketClass(actualType));
    }

    @Override
    public JsonElement serialize(Packet packet, Type type, JsonSerializationContext jsonSerializationContext) {
        JsonElement element = jsonSerializationContext.serialize(packet);
        element.getAsJsonObject()
                .addProperty("type", getPacketType(packet));
        return element;
    }

}
