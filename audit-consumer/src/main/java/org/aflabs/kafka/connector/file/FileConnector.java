package org.aflabs.kafka.connector.file;

import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.sink.SinkConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileConnector extends SinkConnector {

    private static Logger log = LoggerFactory.getLogger(FileConnector.class);
    private FileConfig fileConfig;

    @Override
    public void start(Map<String, String> map) {
        fileConfig = new FileConfig(map);
    }

    @Override
    public Class<? extends Task> taskClass() {
        return FileTask.class;
    }

    @Override
    public List<Map<String, String>> taskConfigs(int maxTasks) {
        List<Map<String,String>> taskConfigs = new ArrayList<>();
        log.info("Creating taskConfig with maxTasks {}",maxTasks);
        //Creates separated configs for the different tasks.
        for(int i = 0; i < maxTasks; i++)
        {
            Map<String,String> newTaskConfig = new HashMap<>();
            newTaskConfig.putAll(fileConfig.originalsStrings());
            //newTaskConfig.put("task.id.internal",""+i);
            taskConfigs.add(newTaskConfig);
        }

        return taskConfigs;
    }

    @Override
    public void stop() {

    }

    @Override
    public ConfigDef config() {
        return FileConfig.conf();
    }

    @Override
    public String version() {
        return "1.0";
    }
}
