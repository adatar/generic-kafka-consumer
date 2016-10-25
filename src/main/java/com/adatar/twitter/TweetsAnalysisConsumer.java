package com.adatar.twitter;

import com.google.gson.Gson;
import com.adatar.persist.mongo.MongoInsert;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import javax.annotation.PreDestroy;
import java.util.*;

/**
 * This is the consumer thread of the TweetsAnalysis Consumer. It runs indefinitely and polls for new messages. Once it receives new messages
 * it processes it and persists it by calling mongoInsert's method with the message as a parameter
 */

@Component
@Scope("prototype")
public class TweetsAnalysisConsumer implements Runnable{

    private static Logger LOGGER = Logger.getLogger(TweetsAnalysisConsumer.class.getName());

    private Gson gson;

    private KafkaConsumer<String, String> consumer;

    private MongoInsert mongoInsert;

    public TweetsAnalysisConsumer(List<String> topics, Properties kafkaProperties, MongoInsert mongoInsert){
        this.consumer = new KafkaConsumer<>(kafkaProperties);
        this.consumer.subscribe(topics);
        this.mongoInsert = mongoInsert;
        this.gson = new Gson();
    }

    public void run() {
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(500);

            if(records.count() != 0)
                LOGGER.info(records.count() + " records received");

            for (ConsumerRecord<String, String> record : records) {
                String message = record.value();
                String processedMessage = process(message);
                persist(processedMessage);
            }
        }
    }

    private String process(String message){
        return message;
    }

    private void persist(String message){

        try{
            mongoInsert.persist(message);

        } catch (Exception e){
            LOGGER.error("Could not parse response" + message);
            return;
        }
    }

    @PreDestroy
    private void destroy(){
        consumer.close();
    }
}
