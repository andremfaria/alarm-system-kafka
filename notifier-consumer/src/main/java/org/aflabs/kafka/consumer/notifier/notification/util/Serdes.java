package org.aflabs.kafka.consumer.notifier.notification.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;


import java.io.IOException;

public class Serdes {

    public static ObjectNode getJsonMessage(String value)
    {
        ObjectNode node = null;
        try {
            node = new ObjectMapper().readValue(new String(value.getBytes()), ObjectNode.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return node;
    }
}
