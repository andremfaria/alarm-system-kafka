package org.aflabs.kafka.consumer.notifier.notification.cfg;

import java.io.*;
import java.util.Properties;

public class NotificationCfg {

    //STATIC
    private static final String EMAIL_FILE = "application.properties";
    private static final String SMTP_SERVER = "smtpServer";
    private static final String PORT = "port";
    private static final String EMAIL_ORIGIN = "emailOrigin";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String EMAIL_DEST = "emailDestination";
    private static final String TELEGRAM_ON="telegramNotification";
    private static final String TELEGRAM_TOKEN="telegramToken";
    private static final String TELEGRAM_CHATID="telegramChatId";



    //instance variables
    private String smtpServer;
    private String port;
    private String emailOrigin;

    @Override
    public String toString() {
        return "NotificationCfg{" +
                "smtpServer='" + smtpServer + '\'' +
                ", port='" + port + '\'' +
                ", emailOrigin='" + emailOrigin + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", emailDestination='" + emailDestination + '\'' +
                ", telegramOn='" + telegramOn + '\'' +
                ", telegramNotification='" + telegramToken + '\'' +
                '}';

    }

    private String username;
    private String password;
    private String emailDestination;
    private String telegramOn;
    private String telegramToken;
    private String telegramChatid;


    public NotificationCfg() {

        try {

            Properties props = new Properties();
            props.load(new InputStreamReader(getClass().getClassLoader().
                    getResourceAsStream(EMAIL_FILE)));

            //email
            this.smtpServer = props.getProperty(SMTP_SERVER);
            this.port = props.getProperty(PORT);
            this.emailOrigin = props.getProperty(EMAIL_ORIGIN);
            this.username = props.getProperty(USERNAME);
            this.password = props.getProperty(PASSWORD);
            this.emailDestination = props.getProperty(EMAIL_DEST);

            //telegram token
            this.telegramOn = props.getProperty(TELEGRAM_ON);
            this.telegramToken = props.getProperty(TELEGRAM_TOKEN);
            this.telegramChatid = props.getProperty(TELEGRAM_CHATID);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String getSmtpServer() {
        return smtpServer;
    }

    public void setSmtpServer(String smtpServer) {
        this.smtpServer = smtpServer;
    }

    public String getEmailOrigin() {
        return emailOrigin;
    }

    public void setEmailOrigin(String emailOrigin) {
        this.emailOrigin = emailOrigin;
    }

    public String getUserName() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailDestination() {
        return emailDestination;
    }

    public void setEmailDestination(String emailDestination) {
        this.emailDestination = emailDestination;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getTelegramToken() {
        return telegramToken;
    }

    public void setTelegramToken(String telegramNotification) {
        this.telegramToken = telegramNotification;
    }

    public String getTelegramOn() {
        return telegramOn;
    }

    public void setTelegramOn(String telegramOn) {
        this.telegramOn = telegramOn;
    }

    public String getTelegramChatid() {
        return telegramChatid;
    }

    public void setTelegramChatid(String telegramChatid) {
        this.telegramChatid = telegramChatid;
    }
}
