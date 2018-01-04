package com.apixandru.rummikub.swing.websocket;

import com.apixandru.rummikub.api.room.RummikubRoomListener;
import com.apixandru.rummikub.brotocol.ConnectorPacketHandler;
import com.apixandru.rummikub.brotocol.Packet;
import com.apixandru.rummikub.brotocol.PacketWriter;
import com.apixandru.rummikub.brotocol.game.client.LoginRequest;
import com.apixandru.rummikub.brotocol.game.server.LoginResponse;
import com.apixandru.rummikub.brotocol.util.PacketHandlerAware;
import com.apixandru.rummikub.brotocol.websocket.JsonBrotocol;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.DeploymentException;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@ClientEndpoint(encoders = JsonBrotocol.class, decoders = JsonBrotocol.class)
public class RummikubClient implements PacketWriter, PacketHandlerAware {

    private static final int STATE_DISCONNECTED = 0;
    private static final int STATE_CONNECTED = 1;
    private static final int STATE_LOGGED_IN = 2;
    private static final int STATE_IN_GAME = 3;

    private final Logger logger = Logger.getLogger(this.getClass().getName());

    private final List<OnConnectListener> connectListeners = new ArrayList<>();
    private final List<OnLoginListener> loginListeners = new ArrayList<>();

    private int state;

    private Session session;

    private ConnectorPacketHandler packetHandler;

    public void send(Packet text) throws IOException {
        try {
            session.getBasicRemote()
                    .sendObject(text);
        } catch (IOException | EncodeException ex) {
            close(4010, "Failed to send message.");
        }
    }

    public void close(int code, String reason) throws IOException {
        session.close(new CloseReason(() -> code, reason));
    }

    public void connect(String server) throws DeploymentException, URISyntaxException, IOException {
        checkState(STATE_DISCONNECTED);
        URI uri = new URI("ws://" + server + ":8080/websocket");
        ContainerProvider.getWebSocketContainer()
                .connectToServer(this, uri);
    }

    public boolean isConnected() {
        return session != null;
    }

    private void checkState(int state) {
        if (!stateIs(state)) {
            throw new IllegalStateException("Expecting state " + state + " but it's actually " + this.state);
        }
    }

    private boolean stateIs(int state) {
        return state == this.state;
    }

    private void swapState(int from, int to) {
        checkState(from);
        this.state = to;
    }

    @OnOpen
    public void onOpen(Session session) throws IOException {
        swapState(STATE_DISCONNECTED, STATE_CONNECTED);
        logger.info("Connected ... " + session.getId());
        this.session = session;
        for (OnConnectListener connectListener : connectListeners) {
            connectListener.onConnected();
        }
    }

    @OnMessage
    public void onMessage(Packet packet, Session session) throws IOException {
        if (packet instanceof LoginResponse) {
            handleLoginResponse((LoginResponse) packet);
            return;
        }
        packetHandler.handle(packet);
    }

    private void handleLoginResponse(LoginResponse response) {
        if (response.accepted) {
            for (OnLoginListener loginListener : loginListeners) {
                loginListener.onLoggedIn();
            }
        } else {
            for (OnLoginListener loginListener : loginListeners) {
                loginListener.onLogInRejected(response.reason);
            }
        }
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason) throws IOException {
        logger.info(String.format("Session %s close because of %s", session.getId(), closeReason));
    }

    @OnError
    public void onError(Session session, Throwable thr) {
        System.out.println("SHOES");
        thr.printStackTrace();
    }

    public void addOnConnectListener(OnConnectListener onConnectListener) {
        this.connectListeners.add(onConnectListener);
    }

    public void login(String username) throws IOException {
        LoginRequest request = new LoginRequest();
        request.playerName = username;
        send(request);
    }

    public void addOnLoginListener(OnLoginListener listener) {
        loginListeners.add(listener);
    }

    @Override
    public void writePacket(Packet packet) {
        try {
            send(packet);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to send packet", e);
        }
    }

    @Override
    public void close() throws IOException {
        session.close();
    }

    @Override
    public void setPacketHandler(ConnectorPacketHandler packetHandler) {
        this.packetHandler = packetHandler;
    }

}
