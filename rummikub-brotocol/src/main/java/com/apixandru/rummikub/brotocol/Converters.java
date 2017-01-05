package com.apixandru.rummikub.brotocol;

import com.apixandru.rummikub.api.game.Card;
import com.apixandru.rummikub.api.game.GameOverReason;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import static com.apixandru.rummikub.api.game.Constants.CARDS;

/**
 * @author Alexandru-Constantin Bledea
 * @since Apr 23, 2016
 */
final class Converters {

    private Converters() {
    }

    static Card readCard(final DataInput input) throws IOException {
        return CARDS.get(input.readByte());
    }

    static void writeCard(final Card data, final DataOutput output) throws IOException {
        writeSafeByte(CARDS.indexOf(data), output);
    }

    static void writeSafeByte(final int data, final DataOutput output) throws IOException {
        final byte byteValue = (byte) data;
        if (byteValue != data) {
            throw new IllegalArgumentException();
        }
        output.writeByte(byteValue);
    }

    static int readByte(final DataInput input) throws IOException {
        return input.readByte();
    }

    static GameOverReason readGameOverReason(final DataInput input) throws IOException {
        return GameOverReason.values()[readByte(input)];
    }

    static void writeGameOverReason(final GameOverReason gameOverReason, final DataOutput output) throws IOException {
        writeSafeByte(gameOverReason.ordinal(), output);
    }

    static Integer readInteger(final DataInput input) throws IOException {
        final int i = readByte(input);
        return -1 == i ? null : i;
    }

    static void writeInteger(final Integer integer, final DataOutput output) throws IOException {
        ensureNotNegative(integer);
        final int data = null == integer ? -1 : integer;
        writeSafeByte(data, output);
    }

    static boolean readBoolean(final DataInput input) throws IOException {
        return input.readBoolean();
    }

    static void writeBoolean(final boolean data, final DataOutput output) throws IOException {
        output.writeBoolean(data);
    }

    static String readString(final DataInput input) throws IOException {
        return input.readUTF();
    }

    static void writeString(final String data, final DataOutput output) throws IOException {
        output.writeUTF(null == data ? "" : data);
    }

    static int readInt(final DataInput input) throws IOException {
        return input.readInt();
    }

    static void writeInt(final int data, final DataOutput output) throws IOException {
        output.writeInt(data);
    }

    private static void ensureNotNegative(final Integer integer) {
        if (null != integer && integer < 0) {
            throw new IllegalArgumentException();
        }
    }

}
