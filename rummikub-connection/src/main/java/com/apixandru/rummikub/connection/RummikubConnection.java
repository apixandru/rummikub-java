package com.apixandru.rummikub.connection;

import com.apixandru.rummikub.brotocol.Packet;
import com.apixandru.rummikub.brotocol.PacketReader;
import com.apixandru.rummikub.brotocol.PacketWriter;
import com.apixandru.rummikub.brotocol.RummikubSerializer;
import com.apixandru.rummikub.brotocol.Serializer;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * @author Alexandru-Constantin Bledea
 * @since Aug 18, 2016
 */
public class RummikubConnection implements PacketReader, PacketWriter {

    private final Serializer serializer = new RummikubSerializer();

    private final Socket socket;
    private final DataInputStream in;
    private final DataOutputStream out;

    public RummikubConnection(Socket socket) throws IOException {
        this.socket = socket;
        this.out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));
        this.out.flush(); // inform consumer that we're a data stream

        this.in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
    }


    @Override
    public void writePacket(Packet packet) {
        synchronized (out) {
            try {
                serializer.serialize(packet, out);
            } catch (IOException e) {
                throw new IllegalStateException(e);
            }
        }
    }

    @Override
    public Packet readPacket() throws IOException {
        return serializer.deserialize(in);
    }

    @Override
    public void close() throws IOException {
        this.in.close();
        this.out.close();
        this.socket.close();
    }

}
