package com.example.realtimechat.TelegramBots;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.java_websocket.WebSocket;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import com.example.realtimechat.Model.Chat;

import java.time.LocalDateTime;
import java.util.Set;

import static com.example.realtimechat.connectDB.mongoUri;

public class TeleBot extends TelegramLongPollingBot {
    //region Telegram Bot ConnectDB
    MongoClientURI uri = new MongoClientURI(mongoUri);
    MongoClient client = new MongoClient(uri);
    MongoDatabase database = client.getDatabase("testDB");
    MongoCollection<Document> chatCollection = database.getCollection(Chat.CHAT_BOX);
    //endregion
    private Set<WebSocket> conns;
    private String registeredDomain;

    public TeleBot(Set<WebSocket> conns, String registeredDomain) {
        this.conns = conns;
        this.registeredDomain = registeredDomain;
    }

    @Override
    public String getBotUsername() {
        return "qunaa_bot";
    }
    @Override
    public String getBotToken() {
        return "6058554744:AAHtq_Eolv62h3aTuxt-dmEiCx4LwuvuHno";
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().isGroupMessage()) {
            if (conns.size() > 0) {
                for (WebSocket sock : conns) {
                    sock.send(update.getMessage().getText());
                }
            } else {
                System.out.println("No connections");
            }

            Chat chat = new Chat();
            chat.setSender("[ADMIN]: " + update.getMessage().getFrom().getFirstName());
            chat.setContent(update.getMessage().getText());
            chat.setCreatedAt(LocalDateTime.now());
            chatCollection.insertOne(chat);
        }
    }
    //-863634181

//    public void checkDomainRegistered(String message) {
//        if (userRegistraion.find(Filters.eq("domain", registeredDomain)).first() != null) {
//            getMessageFromWeb(message);
//        }
//    }
    //1855304872
    public void getMessageFromWeb(String chat){
        SendMessage message = new SendMessage();
        message.setChatId("-863634181");
        message.setText(chat);

        try {
            executeAsync(message); // Call method to send the message
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
