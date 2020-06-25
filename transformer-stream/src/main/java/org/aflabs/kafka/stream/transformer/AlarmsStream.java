package org.aflabs.kafka.stream.transformer;

import org.aflabs.kafka.stream.transformer.streams.GenericStream;
import org.aflabs.kafka.stream.transformer.streams.impl.AlarmStream;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class AlarmsStream {

    static Logger log = LoggerFactory.getLogger(AlarmStream.class);

    /**
     *
     * @param args args[0] -> bootstrap servers, args[1] -> Application ID
     */
    public static void main(String[] args) {
        Properties config = new Properties();
        config.put(StreamsConfig.APPLICATION_ID_CONFIG, args[1]);
        config.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, args[0]);
        config.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        config.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        config.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        //config.put(
          //      StreamsConfig.DEFAULT_DESERIALIZATION_EXCEPTION_HANDLER_CLASS_CONFIG,
            //    LogAndContinueExceptionHandler.class);
        //config.put(StreamsConfig.DEFAULT_PRODUCTION_EXCEPTION_HANDLER_CLASS_CONFIG, ProductionExceptionHandler.class);

        log.info("Initiating streams. Properties: {} ",config);
        GenericStream stream = new AlarmStream("host_stats","alarms",config);
        stream.initStream();

        //print topology
        log.info("Printing topology");
        stream.getKafkaStreams().localThreadsMetadata().forEach(data -> System.out.println(data));

        //Correctly close stream
        Runtime.getRuntime().addShutdownHook(new Thread(stream::stopStream));
    }
}
