package com.adatar.util;

public class GlobalConstants {

    public static final String KAFKA_GROUP = "group.id";
    public static final String KafkaAutoCommitTime = "auto.commit.interval.ms";
    public static final String KafkaAutoOffsets = "auto.offset.reset";
    public static final String KafkaBootstrapServers = "bootstrap.servers";
    public static final String KafkaKeyDeserializer = "key.deserializer";
    public static final String KafkaValueDeserializer = "value.deserializer";

    public static final String MONGO_HOST = "mongo.host";
    public static final String MONGO_PORT = "mongo.port";
    public static final String MONGO_DATABASE = "mongo.database";

    public static final String TWEETS_ANALYSIS = "tweets-analysis";
    public static final String TWEETS_COLLECTION = "mongo.collection.twitter";
    public static final String TWEETS_ANALYSIS_KAFKA_PARTITIONS = "kafka.partitions.twitter";
    public static final String TWEETS_ANALYSIS_CONSUMER_THREADS = "consumer.twitter.threads";

}
