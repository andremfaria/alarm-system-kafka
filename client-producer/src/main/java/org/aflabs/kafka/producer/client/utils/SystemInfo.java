package org.aflabs.kafka.producer.client.utils;

import java.lang.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class SystemInfo {

    private static OperatingSystemMXBean systemBean = ManagementFactory.getOperatingSystemMXBean();
    private static com.sun.management.OperatingSystemMXBean beanMemory =
            (com.sun.management.OperatingSystemMXBean)
                    java.lang.management.ManagementFactory.getOperatingSystemMXBean();

    //Average CPU last minute. Using java.lang With com.sun always returning 0.0
    public static Double getCpuUsage(){
        return (double) Math.round(systemBean.getSystemLoadAverage() * 100) / 100;
    }

    //Need to use com.sun because the newest java.lang was not working, not accessible.
    public static Double getMemUsage()
    {
        return (double) Math.round(beanMemory.getFreePhysicalMemorySize()/1024/1024);
    }

    public static String getHostName()
    {
        try {
           return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return null;
    }
}
