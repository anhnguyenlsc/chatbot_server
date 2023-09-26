package com.example.realtimechat;

import com.example.realtimechat.Server.webSocketServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RealtimeChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(RealtimeChatApplication.class, args);
        new webSocketServer().start();
    }
}
