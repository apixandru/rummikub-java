package com.apixandru.rummikub.swing.websocket;

public interface OnLoginListener {

    void onLoggedIn();

    void onLogInRejected(String reason);

}
