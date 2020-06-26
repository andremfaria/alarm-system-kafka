package org.aflabs.kafka.stream.transformer.conf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class ConfigBean {


    private static Logger log = LoggerFactory.getLogger(ConfigBean.class);
    //Static config variables
    private static final String ALARMS_FILE = "alarms.properties";
    private static final String MEM_CONF = "memory";
    private static final String CPU_CONF = "cpu";
    private static final String RETENTION_CONF = "retention";

    //Instance variables
    private Double alarmMemory;
    private Double alarmCpu;
    private Long retentionTime;


    public ConfigBean()
    {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getClass().getClassLoader().
                    getResourceAsStream(ALARMS_FILE)));
            String line;

            while((line= reader.readLine())!=null)
            {
                String[] configValue = line.split("=");
                log.info("Loading configuration from file: {}", line);
                switch(configValue[0])
                {
                    case MEM_CONF:
                        this.alarmMemory= Double.valueOf(configValue[1]);
                        break;
                    case CPU_CONF:
                        this.alarmCpu= Double.valueOf(configValue[1]);
                        break;
                    case RETENTION_CONF:
                        this.retentionTime= Long.valueOf(configValue[1]);
                        break;
                    default: break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            //Assuming default values
            this.retentionTime=300l;
            this.alarmCpu=10.0;
            this.alarmMemory=12.0;

        }
    }
    public Double getAlarmMemory() {
        return alarmMemory;
    }

    public void setAlarmMemory(Double alarmMemory) {
        this.alarmMemory = alarmMemory;
    }

    public Double getAlarmCpu() {
        return alarmCpu;
    }

    public void setAlarmCpu(Double alarmCpu) {
        this.alarmCpu = alarmCpu;
    }

    public Long getRetentionTime() {
        return retentionTime;
    }

    public void setRetentionTime(Long retentionTime) {
        this.retentionTime = retentionTime;
    }
}
