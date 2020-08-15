package me.adam.volticlobby.utils;

import com.mongodb.MongoClientURI;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.conversions.Bson;
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

    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public void checkForProfile(UUID uuid){
        Document profile = collection.find(new Document("uuid", uuid.toString())).first();
        if(profile == null){
            Date date = new Date();

            Document newProfile = new Document("uuid", uuid.toString());
            newProfile.append("xp", 0);
            newProfile.append("firstJoined", dateFormat.format(date));
            newProfile.append("lastPlayed", dateFormat.format(date));
            collection.insertOne(newProfile);
        }else{
            Date date = new Date();

            Bson updateValue = new Document("lastPlayed", dateFormat.format(date));
            Bson updateOp = new Document("$set", updateValue);

            collection.updateOne(profile, updateOp);
        }
    }

    public String getJoined(UUID uuid){
        Document profile = collection.find(new Document("uuid", uuid.toString())).first();
        return profile.getString("firstJoined");
    }

    public String getPlayed(UUID uuid){
        Document profile = collection.find(new Document("uuid", uuid.toString())).first();
        return profile.getString("lastPlayed");
    }

    public int getXP(UUID uuid){
        Document profile = collection.find(new Document("uuid", uuid.toString())).first();
        return profile.getInteger("xp");

    }


}
