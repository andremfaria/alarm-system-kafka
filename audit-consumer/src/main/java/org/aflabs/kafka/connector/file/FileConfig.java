package org.aflabs.kafka.connector.file;

import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigDef.*;

import java.util.Map;

public class FileConfig extends AbstractConfig {

    private static final String INPUT_TOPIC_CONFIG = "topics";
    private static final String INPUT_TOPIC_DOC = "Topic to read";

    private static final String FILE_OUTPUT = "file";
    private static final String FILE_OUTPUT_DOC = "File to audit messages";

    public FileConfig(Map<?, ?> originals) {
        super(conf(),originals);
    }

    public static ConfigDef conf()
    {
        return new ConfigDef()
                .define(INPUT_TOPIC_CONFIG, Type.STRING, Importance.HIGH,INPUT_TOPIC_DOC)
                .define(FILE_OUTPUT,Type.STRING,Importance.HIGH,FILE_OUTPUT_DOC);
    }

    public String getInputTopicConfig()
    {
        return this.getString(INPUT_TOPIC_CONFIG);
    }

    public String getFileOutput()
    {
        return this.getString(FILE_OUTPUT);
    }
}
