package com.example.realtimechat.Server;

import java.io.IOException;
import java.net.ServerSocket;

public class Server {
    public static void main(String[] args) throws IOException {
        try (ServerSocket serverSocket = new ServerSocket(3500)) {
            Protocol p = new Protocol();

            ServerThread userChat = new ServerThread(serverSocket.accept(), p);
            userChat.start();

        } catch (IOException e) {
            System.out.println("Could not listen on port: 3500");
            System.exit(-1);
        }
    }
}
