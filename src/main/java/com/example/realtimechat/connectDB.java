package com.example.realtimechat;

import com.example.realtimechat.Model.Chat;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication

public class connectDB {
    public static String mongoUri;

    public connectDB(@Value("${spring.data.mongodb.uri}") String mongoUri) {
        connectDB.mongoUri = mongoUri;
    }
}
