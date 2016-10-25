package com.adatar.persist.mongo;

import com.adatar.util.GlobalConstants;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Arrays;

@Component
public class MongoConnection {

    private static Logger LOGGER = Logger.getLogger(MongoConnection.class.getName());

    private MongoClient mongoClient;

    @PostConstruct
    public void init(){

        String host = "";
        int port = NumberUtils.toInt("", 27017);

        try{
            mongoClient = new MongoClient(Arrays.asList(new ServerAddress(host, port)));
        } catch (Exception e){
            LOGGER.error("Could not connect to Mongo");
            System.exit(1);
        }

        LOGGER.info("Mongo client created: " + host + ":" + port);
    }

    public MongoClient getClient(){
        return mongoClient;
    }

    @PreDestroy
    private void destroy(){
        mongoClient.close();
    }
}
