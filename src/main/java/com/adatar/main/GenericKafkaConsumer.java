package com.adatar.main;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import com.adatar.util.ConsumerPropertiesLoader;

@Component
public class GenericKafkaConsumer {

    private static Logger LOGGER = Logger.getLogger(GenericKafkaConsumer.class.getName());

    @Autowired
    private ConsumerDelegator consumerDelegator;

    public static void main(String args[]) {

        // TODO: Setup Jcommander

        if(args.length < 2){
            LOGGER.error("Incorrect number of parameters. Usage: <Property_file_path> <topic_name> <consumer_group>");
            System.exit(1);
        }

        ConsumerPropertiesLoader.load(args[0]);

        final ConfigurableApplicationContext context = new ClassPathXmlApplicationContext("application-context.xml");
        context.getBean(GenericKafkaConsumer.class).consumerDelegator.handle(args[1]);
        context.close();
    }
}
