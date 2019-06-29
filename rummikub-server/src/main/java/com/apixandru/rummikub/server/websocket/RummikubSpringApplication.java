package com.apixandru.rummikub.server.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@EnableWebSocket
@SpringBootApplication
public class RummikubSpringApplication implements WebSocketConfigurer {

    @Autowired
    private RummikubWebSocketHandler socketHandler;

    public static void main(String[] args) {
        SpringApplication.run(RummikubSpringApplication.class, args);
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(socketHandler, "/websocket")
                .setAllowedOrigins("*");
    }

}
