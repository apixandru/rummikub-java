package com.apixandru.rummikub.client.connector;

public interface OnLoginListener {

    void onLoggedIn(String playerName);

    void onLogInRejected(String reason);

}
