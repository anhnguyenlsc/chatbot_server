package com.example.realtimechat.Server;

import com.example.realtimechat.Model.Chat;
import com.example.realtimechat.TelegramBots.TeleBot;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.json.*;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import java.net.InetSocketAddress;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static com.example.realtimechat.connectDB.mongoUri;

public class webSocketServer extends WebSocketServer {
    private static int TCP_PORT = 3500;
    public Set<WebSocket> conns;
    public webSocketServer() {
        super(new InetSocketAddress(TCP_PORT));
        conns = new HashSet<>();
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake) {
        conns.add(conn);
        System.out.println("New connection from " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote) {
        conns.remove(conn);
        System.out.println("Closed connection to " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
    }

    private boolean hasRun = false;
    @Override
    public void onMessage(WebSocket conn,String message) {
        JSONObject jsonObject = new JSONObject(message);
        String userId = jsonObject.getString("userid");
        String usrDomain = jsonObject.getString("currentDomain");
        String msgContent = jsonObject.getString("msgContent");

        TeleBot bot = new TeleBot(conns, usrDomain);

        if (!hasRun) {
            try {
                TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
                botsApi.registerBot(new TeleBot(conns, usrDomain));
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
            hasRun = true;
        }
        bot.getMessageFromWeb(msgContent);

        MongoClientURI uri = new MongoClientURI(mongoUri);
        MongoClient client = new MongoClient(uri);
        MongoDatabase database = client.getDatabase("testDB");

        MongoCollection<Document> chatCollection = database.getCollection(Chat.CHAT_BOX);
        try {
            Chat chat = new Chat();
            chat.setUserid(userId);
            chat.setUsrDomain(usrDomain);
            chat.setContent(msgContent);
            chat.setCreatedAt(LocalDateTime.now());
            chatCollection.insertOne(chat);
        } catch (Exception e) {
            System.out.println("ERROR!!!");
        }
    }


    @Override
    public void onError(WebSocket conn, Exception ex) {
        //ex.printStackTrace();
        if (conn != null) {
            conns.remove(conn);
            // do something if required
        }
        assert conn != null;
        System.out.println("ERROR from " + conn.getRemoteSocketAddress().getAddress().getHostAddress());
    }

    @Override
    public void onStart() {

    }
}
