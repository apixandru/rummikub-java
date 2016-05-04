package com.apixandru.rummikub.brotocol;

import com.apixandru.rummikub.api.Card;
import com.apixandru.rummikub.api.GameOverReason;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import static com.apixandru.rummikub.api.Constants.CARDS;

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

    private static void ensureNotNegative(final Integer integer) {
        if (null != integer && integer < 0) {
            throw new IllegalArgumentException();
        }
    }

}