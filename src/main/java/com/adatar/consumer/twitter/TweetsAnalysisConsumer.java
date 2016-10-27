package com.adatar.consumer.twitter;

import com.google.gson.Gson;
import com.adatar.persist.mongo.MongoInsert;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private TweetSparkProcessor tweetSparkProcessor;

    private Gson gson;

    private KafkaConsumer<String, String> consumer;

    private MongoInsert mongoInsert;

    private int threadId;

    public TweetsAnalysisConsumer(List<String> topics, Properties kafkaProperties, MongoInsert mongoInsert, int threadId){
        this.consumer = new KafkaConsumer<>(kafkaProperties);
        this.consumer.subscribe(topics);
        this.mongoInsert = mongoInsert;
        this.threadId = threadId;
        this.gson = new Gson();
    }

    public void run() {
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(500);

            if(records.count() != 0) {
                LOGGER.info(records.count() + " records received by thread: " + threadId);

                String processedMessage = process(records);
                persist(processedMessage);
            }
        }
    }

    private String process(ConsumerRecords<String, String> records){

        List<String> messages = new ArrayList<>();

        for (ConsumerRecord<String, String> record : records) {
            String message = record.value();
            messages.add(message);
        }

        return tweetSparkProcessor.process(messages);
    }

    private void persist(String message){
        mongoInsert.persist(message);
    }

    @PreDestroy
    private void destroy(){
        consumer.close();
    }
}
