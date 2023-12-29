package org.aflabs.kafka.consumer.notifier.processor;

import org.aflabs.kafka.consumer.notifier.notification.Notification;
import org.aflabs.kafka.consumer.notifier.notification.NotificationFactory;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;

public class Processor implements Runnable {

    private static Logger log = LoggerFactory.getLogger(Processor.class);

    private ConsumerRecords<String,String> consumerRecords;


    Notification test = NotificationFactory.getNotification("email");

    public Processor(ConsumerRecords<String,String> consumerRecords)
    {
        this.consumerRecords = consumerRecords;
    }
    @Override
    public void run() {

        ConsumerRecord<String,String> consumerRecord;
        Iterator<ConsumerRecord<String, String>> iterator = consumerRecords.iterator();

        while(iterator.hasNext())
        {
            consumerRecord = iterator.next();
            log.info("Process message: Key -> {} Message -> {}",consumerRecord.key(),consumerRecord.value());

            if(consumerRecord.value() == null)
                continue;

            NotificationFactory.getNotification("email").send(consumerRecord.key(),consumerRecord.value());
            NotificationFactory.getNotification("telegram").send(consumerRecord.key(),consumerRecord.value());
            //Email.sendEmail(SUBJECT+consumerRecord.key(),CONTENT+consumerRecord.value());
        }

    }

    public ConsumerRecords<String, String> getConsumerRecords() {
        return consumerRecords;
    }

    public void setConsumerRecords(ConsumerRecords<String, String> consumerRecords) {
        this.consumerRecords = consumerRecords;
    }
}
