package com.adatar.persist.mongo;

import com.mongodb.client.MongoDatabase;
import com.adatar.util.GlobalConstants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;

@Component
public class MongoDatabaseClient {

    private static Logger LOGGER = Logger.getLogger(MongoDatabaseClient.class.getName());

    private MongoDatabase mongoDatabase;

    @Autowired
    private MongoConnection mongoConnection;

    @PostConstruct
    public void init(){
        String dataBase = "";

        try{

            mongoDatabase = mongoConnection.getClient().getDatabase(dataBase);

        } catch (Exception e){
            LOGGER.error("The specified database does not exist: " + dataBase);
            System.exit(1);
        }

        LOGGER.info("Connection to " + dataBase + " opened");
    }

    public MongoDatabase getMongoDbClient(){
        return mongoDatabase;
    }
}
