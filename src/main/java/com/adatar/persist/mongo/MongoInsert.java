package com.adatar.persist.mongo;

import com.mongodb.client.MongoCollection;
import org.apache.log4j.Logger;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * This is a prototype class which inserts data to a specific collection defined in the constructor.
 */

@Component
@Scope("prototype")
public class MongoInsert {

    private static Logger LOGGER = Logger.getLogger(MongoInsert.class.getName());

    @Autowired
    private MongoDatabaseClient mongoDatabaseClient;

    private MongoCollection mongoCollection;

    private String collectionName;

    public MongoInsert(String collectionName){
        this.collectionName = collectionName;
    }

    @PostConstruct
    public void init() {
        try {
            mongoCollection = mongoDatabaseClient.getMongoDbClient().getCollection(collectionName);
        } catch (Exception e){
            LOGGER.error("The specified collection does not exist: " + collectionName);
            System.exit(1);
        }
    }

    public void persist(List<String> messages){
        List<Document> documentList = new ArrayList<>();

        for(String message : messages){
            Document document = Document.parse(message);
            documentList.add(document);
        }

        mongoCollection.insertMany(documentList);
    }

    public void persist(String jsonString){
        Document myDoc = Document.parse(jsonString);
        mongoCollection.insertOne(myDoc);
    }
}
