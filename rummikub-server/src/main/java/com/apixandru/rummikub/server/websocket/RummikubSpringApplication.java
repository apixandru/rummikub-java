package com.apixandru.rummikub.server.websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@EnableWebSocket
@SpringBootApplication
public class RummikubSpringApplication implements WebSocketConfigurer {

    public static void main(String[] args) {
        SpringApplication.run(RummikubSpringApplication.class, args);
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new RummikubWebSocketHandler(), "/websocket")
                .setAllowedOrigins("*");
    }

}
