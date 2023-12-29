package org.aflabs.kafka.stream.transformer.conf;

import java.io.*;
import java.util.Properties;

public class ConfigBean {

    // Static config variables
    private static final String ALARMS_FILE = "alarms.properties";
    private static final String MEM_CONF = "memory";
    private static final String CPU_CONF = "cpu";
    private static final String RETENTION_CONF = "retention";

    // Instance variables
    private Double alarmMemory;
    private Double alarmCpu;
    private Long retentionTime;

    public ConfigBean() {
        try {

            Properties props = new Properties();

            props.load(new InputStreamReader(getClass().getClassLoader().getResourceAsStream(ALARMS_FILE)));

            this.alarmMemory = Double.valueOf(props.getProperty(MEM_CONF));
            this.alarmCpu = Double.valueOf(props.getProperty(CPU_CONF));
            this.retentionTime = Long.valueOf(props.getProperty(RETENTION_CONF));

        } catch (IOException e) {
            e.printStackTrace();
            // Assuming default values
            this.retentionTime = 300l;
            this.alarmCpu = 10.0;
            this.alarmMemory = 12.0;

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
