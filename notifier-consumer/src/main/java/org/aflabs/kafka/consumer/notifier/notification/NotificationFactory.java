package org.aflabs.kafka.consumer.notifier.notification;

public class NotificationFactory {

    public static Notification getNotification(String type)
    {
        if(type.equalsIgnoreCase("email"))
            return new Email();
        else if(type.equalsIgnoreCase("telegram"))
            return new Telegram();
        else return null;
    }
}
