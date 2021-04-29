package org.aflabs.kafka.stream.transformer.streams.impl;

import com.fasterxml.jackson.databind.JsonNode;
import org.aflabs.kafka.stream.transformer.beans.AggInfo;
import org.aflabs.kafka.stream.transformer.conf.ConfigBean;
import org.aflabs.kafka.stream.transformer.streams.GenericStream;
import org.aflabs.kafka.stream.transformer.streams.serdes.AggInfoSerdes;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.connect.json.JsonDeserializer;
import org.apache.kafka.connect.json.JsonSerializer;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class AlarmStream extends GenericStream {

    private static Logger log = LoggerFactory.getLogger(AlarmStream.class);
    private String alarmTopic;
    private String inputTopic;
    private ConfigBean configBean;

    public AlarmStream(String inputTopic, String alarmTopic, Properties properties)
    {
        this.inputTopic = inputTopic;
        this.alarmTopic = alarmTopic;
        setProperties(properties) ;
        this.configBean = new ConfigBean();
    }

    @Override
    public StreamsBuilder createTopology() {

        log.info("Creating topology");
        Serializer<JsonNode> jsonSerializer = new JsonSerializer();
        Deserializer<JsonNode> jsonDeserializer = new JsonDeserializer();
        Serde<JsonNode> jsonSerde = Serdes.serdeFrom(jsonSerializer, jsonDeserializer);

        Serializer<AggInfo> aggSerializer = new AggInfoSerdes();
        Deserializer<AggInfo> aggDeserializer = new AggInfoSerdes();
        Serde<AggInfo> aggSerde = Serdes.serdeFrom(aggSerializer, aggDeserializer);

        AggInfo info = new AggInfo();
        log.info("Starting topology:");
        StreamsBuilder streamBuilder = new StreamsBuilder();

        KTable<Windowed<String>, AggInfo> kTable = streamBuilder.stream(inputTopic, Consumed.with(
                Serdes.String(),jsonSerde
        )).
                filter((a,b) -> a != null && b != null)
                .groupByKey()
                .windowedBy(TimeWindows.of(configBean.getRetentionTime()))
                .aggregate(
                        () -> new AggInfo(),
                        (aggKey,newValue,aggValue) ->  aggValue.updateNode(newValue),
                        Materialized.with(Serdes.String(),aggSerde)
                )
                .filter((k,v) -> v.getAvgCpu() > configBean.getAlarmCpu() ||
                        v.getAvgMem() < configBean.getAlarmMemory());

        //Need to put map again due to windowed key.
        kTable.toStream().
                 filter((key,value) -> value != null)
                .map((key,value)-> KeyValue.pair(key.key(),value)).
                to(alarmTopic, Produced.with(Serdes.String(),aggSerde));

        return streamBuilder;
    }


    public String getInputTopic() {
        return inputTopic;
    }

    public void setInputTopic(String inputTopic) {
        this.inputTopic = inputTopic;
    }

    public String getAlarmTopic() {
        return alarmTopic;
    }

    public void setAlarmTopic(String alarmTopic) {
        this.alarmTopic = alarmTopic;
    }

    public static AggInfo getAggInfo()
    {
        return new AggInfo();
    }
}
