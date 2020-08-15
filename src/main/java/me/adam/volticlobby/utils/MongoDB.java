package me.adam.volticlobby.utils;

import com.mongodb.MongoClientURI;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bukkit.Bukkit;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class MongoDB {
    MongoClientURI clientURI = new MongoClientURI("mongodb://server-auth:jAs9Rb0TnRCCqqnH@cluster0-shard-00-00.6hwgj.mongodb.net:27017,cluster0-shard-00-01.6hwgj.mongodb.net:27017,cluster0-shard-00-02.6hwgj.mongodb.net:27017/<dbname>?ssl=true&replicaSet=atlas-ujsvfo-shard-0&authSource=admin&retryWrites=true&w=majority");
    MongoClient mongoClient = new MongoClient(clientURI);

    MongoDatabase mongoDatabase = mongoClient.getDatabase("Voltic");
    MongoCollection<Document> collection = mongoDatabase.getCollection("Global");

    public void checkForProfile(UUID uuid){
        Document profile = collection.find(new Document("uuid", uuid.toString())).first();
        if(profile == null){
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();

            Document newProfile = new Document("uuid", uuid.toString());
            newProfile.append("xp", 0);
            newProfile.append("level", 0);
            newProfile.append("firstJoined", dateFormat.format(date));
            newProfile.append("lastPlayed", dateFormat.format(date));
            collection.insertOne(newProfile);
        }
    }


}
