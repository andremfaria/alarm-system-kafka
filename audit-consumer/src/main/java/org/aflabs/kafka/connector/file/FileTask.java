package org.aflabs.kafka.connector.file;

import org.apache.kafka.connect.sink.SinkRecord;
import org.apache.kafka.connect.sink.SinkTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.Map;

public class FileTask extends SinkTask {

    private static Logger log = LoggerFactory.getLogger(FileTask.class);
    private FileConfig fileConfig;
    private PrintWriter printer;

    @Override
    public String version() {
        return "1.0";
    }

    @Override
    public void start(Map<String, String> map) {
        this.fileConfig = new FileConfig(map);
        log.debug("Initiating task. Configs:{}",map);
        File file = new File(fileConfig.getFileOutput());
        try {
            printer = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void put(Collection<SinkRecord> collection) {
        log.info("Received {} records",collection.size());
        for(SinkRecord record : collection)
        {
            log.info("Processing record: key -> {} value -> {}",record.key(),record.value());
            printer.print(record.key().toString()+",");
            printer.println(record.value());
            printer.flush();
        }
    }

    @Override
    public void stop() {
        log.info("Stopping task");
        printer.flush();
        printer.close();
    }

    public FileConfig getFileConfig() {
        return fileConfig;
    }

    public void setFileConfig(FileConfig fileConfig) {
        this.fileConfig = fileConfig;
    }
}
