package com.apixandru.mocksocket;

import org.junit.Before;
import org.junit.Test;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @author Alexandru-Constantin Bledea
 * @since Aug 15, 2016
 */
public class Tests {

    private static final MockSocketPairFactory socketPairFactory = new MockSocketPairFactory();

    private MockSocketPair socketPair;

    private static void sendViaDataStream(Socket oneSide, Socket otherSide) throws IOException {

        DataOutputStream oneOutputStream = new DataOutputStream(oneSide.getOutputStream());
        DataInputStream otherInputStream = new DataInputStream(otherSide.getInputStream());

        DataTransfer dataTransfer = new DataTransfer(oneOutputStream, otherInputStream, 10);
        dataTransfer.transferRandomInt();
        dataTransfer.transferRandomString();
        dataTransfer.transferRandomLong();
        dataTransfer.transferRandomBoolean();

    }

    @Before
    public void setup() throws IOException {
        socketPair = socketPairFactory.newPair();
    }

    @Test(timeout = 1000)
    public void testSendDataOneToOther() throws IOException {
        Socket socketOneSide = socketPair.getClientSide();
        Socket socketOtherSide = socketPair.getServerSide();

        sendViaDataStream(socketOneSide, socketOtherSide);
        sendViaDataStream(socketOtherSide, socketOneSide);
    }

}
