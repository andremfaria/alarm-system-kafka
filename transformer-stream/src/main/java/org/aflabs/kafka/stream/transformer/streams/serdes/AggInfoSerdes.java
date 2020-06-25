package org.aflabs.kafka.stream.transformer.streams.serdes;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.aflabs.kafka.stream.transformer.beans.AggInfo;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

import java.io.IOException;
import java.util.Map;

public class AggInfoSerdes implements Serializer<AggInfo>, Deserializer<AggInfo> {

    @Override
    public void configure(Map<String, ?> map, boolean b) {
    }

    @Override
    public AggInfo deserialize(String s, byte[] bytes) {
        AggInfo res = new AggInfo();
        try {
            ObjectNode node = new ObjectMapper().readValue(new String(bytes),ObjectNode.class);
            res.setAvgCpu(node.get("avgCpu").doubleValue());
            res.setAvgMem(node.get("avgMemory").doubleValue());
            res.setSumCpu(node.get("sumCpu").doubleValue());
            res.setSumMem(node.get("sumMem").doubleValue());
            res.setCount(node.get("count").intValue());
            return res;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public byte[] serialize(String s, AggInfo aggInfo) {
        ObjectNode node = JsonNodeFactory.instance.objectNode();
        node.put("avgCpu",aggInfo.getAvgCpu());
        node.put("avgMemory", aggInfo.getAvgMem());
        node.put("sumCpu", aggInfo.getSumCpu());
        node.put("sumMem",aggInfo.getSumMem());
        node.put("count",aggInfo.getCount());
        return node.toString().getBytes();
    }

    @Override
    public void close() {

    }
}
