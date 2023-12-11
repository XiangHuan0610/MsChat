package com.chx.chat;

import com.chx.chat.netty.server.WebSocketChatServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BiubiuChatApplication implements CommandLineRunner {

    @Autowired
    private WebSocketChatServer webSocketChatServer;

    public static void main(String[] args) {
        SpringApplication.run(BiubiuChatApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        webSocketChatServer.start();
    }
}
