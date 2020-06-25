package org.aflabs.kafka.producer.client.utils;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;


import java.util.Properties;

public class Producer {

    private String bootstrapServers;
    private String topic;
    private KafkaProducer<String, String> producer;

    public Producer(String bootstrapServers, String topic)
    {
        this.bootstrapServers = bootstrapServers;
        this.topic = topic;
        this.producer = getProducer();
    }

    public KafkaProducer getProducer()
    {

        if(producer != null)
            return producer;

        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,this.bootstrapServers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,StringSerializer.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);
        return producer;
    }
    public void setProducer(KafkaProducer producer) {
        this.producer = producer;
    }


    //Format: hostname, "cpu":12,"mem":50,"timestamp":"1321321312"
    public void sendKpi(String hostName, JsonNode node)
    {
        ProducerRecord<String, String> record = new ProducerRecord<String, String>(this.topic, hostName,node.toString());
        producer.send(record);
        producer.flush();
    }

    public void closeProducer()
    {
        this.producer.flush();
        this.producer.close();
    }


}
