package com.apixandru.rummikub.fieldserializer.converters;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author Alexandru-Constantin Bledea
 * @since Apr 23, 2016
 */
public class BasicConverters {

    public static boolean readBoolean(final DataInput input) throws IOException {
        return input.readBoolean();
    }

    public static void writeBoolean(final boolean data, final DataOutput output) throws IOException {
        output.writeBoolean(data);
    }

    public static String readString(final DataInput input) throws IOException {
        return input.readUTF();
    }

    public static void writeString(final String data, final DataOutput output) throws IOException {
        output.writeUTF(data);
    }

    public static int readInt(final DataInput input) throws IOException {
        return input.readInt();
    }

    public static void writeInt(final int data, final DataOutput output) throws IOException {
        output.writeInt(data);
    }

}
