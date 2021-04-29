package org.aflabs.kafka.consumer.notifier.notification;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sun.mail.smtp.SMTPTransport;
import org.aflabs.kafka.consumer.notifier.notification.util.Serdes;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class Email extends Notification {

    @Override
    public boolean send(String subject, String text) {

        System.out.println("***" + getNotificationCfg());

        String message = buildMessage(subject, text);
        Properties prop = System.getProperties();
        prop.put("mail.smtp.host", getNotificationCfg().getSmtpServer());
        prop.put("mail.smtp.port", getNotificationCfg().getPort());
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");


        Session session = Session.getInstance(prop, null);
        Message msg = new MimeMessage(session);

        try {

            msg.setFrom(new InternetAddress(getNotificationCfg().getEmailOrigin()));
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(getNotificationCfg().getEmailDestination(), false));
            msg.setSubject(subject);
            msg.setText(message);
            msg.setSentDate(new Date());

            SMTPTransport t = (SMTPTransport) session.getTransport("smtp");
            t.connect(getNotificationCfg().getSmtpServer(),
                    getNotificationCfg().getUserName(), getNotificationCfg().getPassword());
            t.sendMessage(msg, msg.getAllRecipients());

            System.out.println("Response: " + t.getLastServerResponse());

            t.close();
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }

        return true;
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
