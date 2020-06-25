package org.aflabs.kafka.consumer.notifier.mail.cfg;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class EmailConfig {

    //STATIC
    private static final String EMAIL_FILE = "application.properties";
    private static final String SMTP_SERVER = "smtpServer";
    private static final String PORT = "port";
    private static final String EMAIL_ORIGIN = "emailOrigin";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String EMAIL_DEST = "emailDestination";


    //instance variables
    private String smtpServer;
    private String port;
    private String emailOrigin;
    private String username;
    private String password;
    private String emailDestination;


    public EmailConfig()
    {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(getClass().getClassLoader().
                    getResource(EMAIL_FILE).getFile()));
            String line;

            while((line= reader.readLine())!=null)
            {
                String[] configValue = line.split("=");
                switch(configValue[0])
                {
                    case SMTP_SERVER:
                        this.smtpServer= configValue[1];
                        break;
                    case PORT:
                        this.port= configValue[1];
                        break;
                    case EMAIL_ORIGIN:
                        this.emailOrigin= configValue[1];
                        break;
                    case USERNAME:
                        this.username= configValue[1];
                        break;
                    case PASSWORD:
                        this.password= configValue[1];
                        break;
                    case EMAIL_DEST:
                        this.emailDestination= configValue[1];
                        break;
                    default: break;
                }
            }
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
}
