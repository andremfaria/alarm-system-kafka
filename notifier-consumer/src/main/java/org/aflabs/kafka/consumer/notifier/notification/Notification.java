package org.aflabs.kafka.consumer.notifier.notification;

import org.aflabs.kafka.consumer.notifier.notification.cfg.NotificationCfg;

public abstract class Notification   {

    private NotificationCfg notificationCfg = new NotificationCfg();


    public NotificationCfg getNotificationCfg() {
        return notificationCfg;
    }

    public void setNotificationCfg(NotificationCfg notificationCfg) {
        this.notificationCfg = notificationCfg;
    }


    public abstract boolean send(String subject, String message);
    public abstract String buildMessage(String host,String messageContent);
}
