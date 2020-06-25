package org.aflabs.kafka.consumer.notifier;

import org.aflabs.kafka.consumer.notifier.processor.Processor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class Consumer {

    private static Logger log = LoggerFactory.getLogger(Consumer.class);
    private KafkaConsumer<String,String> kafkaConsumer;
    private boolean running;

    public Consumer(Properties properties)
    {
        kafkaConsumer = new KafkaConsumer<String,String>(properties);
        running = true;
    }

    public void subscribeTopic(String topicName)
    {
        kafkaConsumer.subscribe(Arrays.asList(topicName));
    }

    public ConsumerRecords<String,String> getRecords()
    {
        return kafkaConsumer.poll(100l);
    }

    public void stopConsumer()
    {
        log.info("Stopping consumer");
        kafkaConsumer.close();
        running = false;
    }

    public KafkaConsumer<String, String> getKafkaConsumer() {
        return kafkaConsumer;
    }

    public void setKafkaConsumer(KafkaConsumer<String, String> kafkaConsumer) {
        this.kafkaConsumer = kafkaConsumer;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    /**
     * @param args args[0]-> bootStrapServer, args[1] -> groupId
     **/

    public static void main(String[] args) {

        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, args[0]);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, args[1]);
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        Consumer consumer = new Consumer(properties);
        log.info("Creating consumer and setting properties.");
        consumer.subscribeTopic("alarms");


        List<Thread> backgroundThreads = new ArrayList<>();
        Thread thread;
        ConsumerRecords<String,String> consumerRecords;

        while(consumer.isRunning())
        {
            log.info("Waiting for messages");
            consumerRecords = consumer.getRecords();
            thread = new Thread(new Processor(consumerRecords));
            log.info("Received messages {}.",consumerRecords.count());
            backgroundThreads.add(thread);
            thread.start();
        }

        //wait all thread finishing
        for (Thread threadAux: backgroundThreads)
        {
            try {
                threadAux.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //Finish consumer in correct way
        Runtime.getRuntime().addShutdownHook(new Thread(consumer::stopConsumer));


    }
}
