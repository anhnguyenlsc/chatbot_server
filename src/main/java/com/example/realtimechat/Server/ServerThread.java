package com.example.realtimechat.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerThread extends Thread{
    private Socket socket = null;
    private Protocol p;

    public ServerThread(Socket socket, Protocol p) {
        super("ServerThread");
        this.socket = socket;
        this.p = p;
    }

    public void run() {

        try (
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(
                                socket.getInputStream()));
        ) {
            String inputLine, outputLine;

            while ((inputLine = in.readLine()) != null) {
                outputLine = p.processInput(inputLine);
                System.out.println(outputLine);
            }
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
