package com.adatar.main;

import com.adatar.util.GlobalConstants;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.adatar.consumer.twitter.TweetsAnalysisHandler;

/**
 * This is a program handler which starts the correct consumer handler based on the provided 'topic'. To define another consumer,
 * handle it in the switch case statement by calling the new consumer handler.
 */

@Component
public class ConsumerDelegator {

    private static Logger LOGGER = Logger.getLogger(ConsumerDelegator.class.getName());

    @Autowired
    private TweetsAnalysisHandler tweetsAnalysisHandler;

    public void handle(String topic){

        switch(topic){
            case GlobalConstants.TWEETS_ANALYSIS:
                tweetsAnalysisHandler.start(topic);
                break;

            default:
                LOGGER.error("Invalid topic. Exiting.");
                System.exit(1);
        }
    }
}
