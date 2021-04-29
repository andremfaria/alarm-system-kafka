package org.aflabs.kafka.consumer.notifier.notification;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.aflabs.kafka.consumer.notifier.notification.util.Serdes;


import java.io.IOException;
import java.net.URL;

public class Telegram extends Notification {

    @Override
    public boolean send(String subject, String content) {


        if(getNotificationCfg().getTelegramOn().equalsIgnoreCase("on"))
        {
            try {
                String message = buildMessage(subject,content);

                URL url = new URL("https://api.telegram.org/bot"+getNotificationCfg().getTelegramToken()+"/sendMessage?chat_id="+
                        getNotificationCfg().getTelegramChatid()+"&text="+message);
                url.getContent();
            } catch (IOException  e) {
                e.printStackTrace();
            }

            return true;
        }

        return false;
    }

    @Override
    public String buildMessage(String host, String messageContent) {
        ObjectNode node = Serdes.getJsonMessage(messageContent);
        StringBuilder builder = new StringBuilder();

        builder.append("New alarm :");
        builder.append("HOST -> ");
        builder.append(host);
        builder.append("CPU -> ");
        builder.append(node.get("avgCpu").asDouble());
        builder.append(" | MEM -> ");
        builder.append(node.get("avgMemory").asDouble());

        return builder.toString();
    }
}
