package org.aflabs.kafka.producer.client;

import org.aflabs.kafka.producer.client.processor.Processor;
import org.aflabs.kafka.producer.client.processor.impl.ProcessorImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Producer {

    private static Logger logger = LoggerFactory.getLogger(Producer.class);

    // args[0] -> bootStrapServers, args[1] -> topic, args[2] -> interval to send kpis
    public static void main(String[] args) {

        logger.debug("Initiating client-producer.");
        Processor proc = new ProcessorImpl(args[0],args[1],Long.valueOf(args[2]));
        Thread producerKafka = new Thread(proc);
        producerKafka.start();

        //Safely close kafka conector.
        Runtime.getRuntime().addShutdownHook(new Thread(proc::closeProducer));

        try {
            producerKafka.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.debug("Terminating client-producer.");

    }
}
