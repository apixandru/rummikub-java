package com.apixandru.mocksocket;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.net.Socket;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Alexandru-Constantin Bledea
 * @since Aug 15, 2016
 */
public final class MockSocketPairFactory {

    private static final int DEFAULT_PIPE_SIZE = 10240;

    private final int pipeSize;

    public MockSocketPairFactory() {
        this(DEFAULT_PIPE_SIZE);
    }

    public MockSocketPairFactory(int pipeSize) {
        this.pipeSize = pipeSize;
    }

    private static Socket newSocket(InputStream inputStream, OutputStream outputStream) throws IOException {
        Socket socket = mock(Socket.class);

        when(socket.getInputStream())
                .thenReturn(inputStream);

        when(socket.getOutputStream())
                .thenReturn(outputStream);

        return socket;
    }

    public MockSocketPair newPair() throws IOException {
        PipedInputStream oneIn = new PipedInputStream(pipeSize);
        PipedOutputStream oneOut = new PipedOutputStream(oneIn);

        PipedInputStream otherIn = new PipedInputStream(pipeSize);
        PipedOutputStream otherOut = new PipedOutputStream(otherIn);


        Socket socketOneSide = newSocket(oneIn, otherOut);
        Socket socketOtherSide = newSocket(otherIn, oneOut);

        return new MockSocketPair(socketOneSide, socketOtherSide);
    }

}
