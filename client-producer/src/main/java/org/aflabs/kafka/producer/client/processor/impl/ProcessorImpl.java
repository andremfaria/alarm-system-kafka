package org.aflabs.kafka.producer.client.processor.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.aflabs.kafka.producer.client.processor.Processor;
import org.aflabs.kafka.producer.client.utils.SystemInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ProcessorImpl extends Processor {

    private static Logger log = LoggerFactory.getLogger(ProcessorImpl.class);
    private long waitTime;

    public ProcessorImpl(String bootstrapServer,String topic,long waitTime)
    {
        super(bootstrapServer, topic);

        this.waitTime = waitTime;
    }

    @Override
    public void runWatcher() {

        while(isRunning())
        {

            log.info("Sending data. Hostname: {}",SystemInfo.getHostName());
            getProducer().sendKpi(SystemInfo.getHostName(),createNode());
            try {
                log.info("Pausing application for {} ms", waitTime);
                Thread.sleep(waitTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private JsonNode createNode()
    {
        ObjectNode node = JsonNodeFactory.instance.objectNode();
        node.put("cpu", SystemInfo.getCpuUsage());
        node.put("mem",SystemInfo.getMemUsage());
        node.put("timestamp",System.currentTimeMillis());
        log.info("Info: {}",node);
        return node;
    }

}
