package org.aflabs.kafka.stream.transformer.streams;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public abstract class GenericStream {

    private static Logger log = LoggerFactory.getLogger(GenericStream.class);
    private KafkaStreams kafkaStreams;
    private Properties properties;

    //implemented in subclasses
    public abstract StreamsBuilder createTopology();

    public void initStream()
    {
        kafkaStreams = new KafkaStreams(createTopology().build(),properties);
        kafkaStreams.start();
    }

    public void stopStream()
    {
        log.info("Stopping streams.");
        kafkaStreams.close();
    }

    public KafkaStreams getKafkaStreams() {
        return kafkaStreams;
    }

    public void setKafkaStreams(KafkaStreams kafkaStreams) {
        this.kafkaStreams = kafkaStreams;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
