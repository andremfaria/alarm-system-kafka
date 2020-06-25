package org.aflabs.kafka.producer.client.processor;

import org.aflabs.kafka.producer.client.utils.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Processor implements Runnable{

    private static Logger log = LoggerFactory.getLogger(Processor.class);
    private Producer producer;
    private boolean isRunning;

    public Processor(String bootStrapServer,String topic)
    {
        producer = new Producer(bootStrapServer,topic);
        isRunning = true;
    }

    public abstract void runWatcher();

    @Override
    public void run()
    {
        log.info("Starting processorImpl thread.");
        this.runWatcher();
    }

    public Producer getProducer() {
        return producer;
    }

    public void setProducer(Producer producer) {
        this.producer = producer;
    }

    public boolean isRunning() {
        return isRunning;
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }

    public void closeProducer()
    {
        log.info("Closing producer.");
        setRunning(false);
        producer.closeProducer();

    }
}
