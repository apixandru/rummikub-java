package com.apixandru.rummikub.swing.websocket;

public interface OnLoginListener {

    void onLoggedIn(String playerName);

    void onLogInRejected(String reason);

}
