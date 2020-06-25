package org.aflabs.kafka.consumer.notifier.mail;

import com.sun.mail.smtp.SMTPTransport;
import org.aflabs.kafka.consumer.notifier.mail.cfg.EmailConfig;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

public class Email {

    private static EmailConfig emailConfig = new EmailConfig();

    public static void sendEmail(String subject, String text)  {
        Properties prop = System.getProperties();
        prop.put("mail.smtp.host", emailConfig.getSmtpServer());
        prop.put("mail.smtp.port", emailConfig.getPort());
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");


        Session session = Session.getInstance(prop, null);
        Message msg = new MimeMessage(session);

        try {

            msg.setFrom(new InternetAddress(emailConfig.getEmailOrigin()));
            msg.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(emailConfig.getEmailDestination(), false));
            msg.setSubject(subject);
            msg.setText(text);
            msg.setSentDate(new Date());

            SMTPTransport t = (SMTPTransport) session.getTransport("smtp");
            t.connect(emailConfig.getSmtpServer(),
                    emailConfig.getUserName(), emailConfig.getPassword());
            t.sendMessage(msg, msg.getAllRecipients());

            System.out.println("Response: " + t.getLastServerResponse());

            t.close();
        }catch(MessagingException e){
                e.printStackTrace();
        }
    }

}
