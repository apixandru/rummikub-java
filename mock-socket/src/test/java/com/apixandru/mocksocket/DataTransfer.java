package com.apixandru.mocksocket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Alexandru-Constantin Bledea
 * @since Aug 15, 2016
 */
class DataTransfer {

    private final DataOutputStream out;
    private final DataInputStream in;

    private final Random random = new Random();

    private final int times;

    DataTransfer(DataOutputStream out, DataInputStream in, int times) {
        this.out = out;
        this.in = in;
        this.times = times;
    }

    void transferRandomInt() throws IOException {
        verifyTransfer(random::nextInt, out::writeInt, in::readInt);
    }

    void transferRandomBoolean() throws IOException {
        verifyTransfer(random::nextBoolean, out::writeBoolean, in::readBoolean);
    }

    void transferRandomLong() throws IOException {
        verifyTransfer(random::nextLong, out::writeLong, in::readLong);
    }

    void transferRandomString() throws IOException {
        verifyTransfer(this::nextString, out::writeUTF, in::readUTF);
    }

    private String nextString() {
        return new BigInteger(130, random).toString(32);
    }

    private <T> void verifyTransfer(Supplier<T> generator,
                                    DataSender<T> sender,
                                    DataReceiver<T> receiver) throws IOException {

        for (int i = 0; i < times; i++) {

            T dataToSend = generator.get();
            sender.send(dataToSend);

            assertThat(receiver.receive())
                    .isEqualTo(dataToSend);
        }

    }

}
