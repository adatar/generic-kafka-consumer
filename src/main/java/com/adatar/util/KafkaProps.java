package com.adatar.util;

import java.util.Properties;

public class KafkaProps {

    public static Properties create(){

        Properties props = new Properties();
        props.put(GlobalConstants.KAFKA_GROUP, ConsumerPropertiesLoader.getProperty(GlobalConstants.KAFKA_GROUP));
        props.put(GlobalConstants.KAFKA_SESSION_TIMEOUT, ConsumerPropertiesLoader.getProperty(GlobalConstants.KAFKA_SESSION_TIMEOUT));
        props.put(GlobalConstants.KafkaSynTime, ConsumerPropertiesLoader.getProperty(GlobalConstants.KafkaSynTime));
        props.put(GlobalConstants.KafkaAutoCommitTime, ConsumerPropertiesLoader.getProperty(GlobalConstants.KafkaAutoCommitTime));
        props.put(GlobalConstants.KafkaAutoOffsets, ConsumerPropertiesLoader.getProperty(GlobalConstants.KafkaAutoOffsets));
        props.put(GlobalConstants.KafkaBootstrapServers, ConsumerPropertiesLoader.getProperty(GlobalConstants.KafkaBootstrapServers));
        props.put(GlobalConstants.KafkaKeyDeserializer, ConsumerPropertiesLoader.getProperty(GlobalConstants.KafkaKeyDeserializer));
        props.put(GlobalConstants.KafkaValueDeserializer, ConsumerPropertiesLoader.getProperty(GlobalConstants.KafkaValueDeserializer));

        return props;
    }
}
