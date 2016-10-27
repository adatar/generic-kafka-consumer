package com.adatar.consumer.twitter;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import com.adatar.persist.mongo.MongoInsert;
import com.adatar.util.ConsumerPropertiesLoader;
import com.adatar.util.GlobalConstants;
import com.adatar.util.KafkaProps;
import com.adatar.util.SpringContext;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * This is the main handler for the indexFailConsumer. It creates consumer threads based on the properties defined.
 */

@Component
public class TweetsAnalysisHandler {

    private static Logger LOGGER = Logger.getLogger(TweetsAnalysisHandler.class.getName());

    private ExecutorService executor;

    private Properties kafkaProperties;

    @PostConstruct
    public void init(){
        this.kafkaProperties = KafkaProps.create();

        int threadPoolSize = NumberUtils.toInt(ConsumerPropertiesLoader.getProperty(GlobalConstants.TWEETS_ANALYSIS_CONSUMER_THREADS), 10);
        this.executor = Executors.newFixedThreadPool(threadPoolSize);
    }

    public void start(String topic){
        MongoInsert mongoInsert = (MongoInsert) SpringContext.getApplicationContext()
                .getBean("mongoInsert", ConsumerPropertiesLoader.getProperty(GlobalConstants.TWEETS_COLLECTION));

        int consumerThreads = NumberUtils.toInt(ConsumerPropertiesLoader.getProperty(GlobalConstants.TWEETS_ANALYSIS_CONSUMER_THREADS), 10);

        for (int i = 0; i < consumerThreads; i++) {
            TweetsAnalysisConsumer consumer = (TweetsAnalysisConsumer) SpringContext.getApplicationContext().getBean("indexFailConsumer", Arrays.asList(topic), kafkaProperties, mongoInsert, i);
            executor.execute(consumer);

            LOGGER.info("Started Consumer # " + i);
        }
    }

    @PreDestroy
    private void destroy(){
        executor.shutdown();
    }
}