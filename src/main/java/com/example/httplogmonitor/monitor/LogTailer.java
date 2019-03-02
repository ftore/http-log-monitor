package com.example.httplogmonitor.monitor;

import com.example.httplogmonitor.domain.ApplicationConfig;
import com.example.httplogmonitor.domain.TerminalArguments;
import com.example.httplogmonitor.repository.AccessLogEntryRepository;
import org.apache.commons.io.input.Tailer;
import org.apache.commons.io.input.TailerListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * Starts and stops tailing access.log file.
 *
 * @author arakhimoff@gmail.com
 */
@Component
public class LogTailer {

    @Value("${appconfig.file.accesslog}")
    private String accessLogFile;

    @Value("${appconfig.requests-per-second}")
    private int reqPerSecond;

    @Autowired
    private AccessLogEntryRepository repository;

    @Autowired
    private ApplicationConfig applicationConfig;

    private Tailer tailer;

    private static final Logger LOGGER = LoggerFactory.getLogger(LogTailer.class);

    /**
     * Starts tailing access.log file
     * @param arguments
     */
    public void startTailer(TerminalArguments arguments) {
        LOGGER.debug("Starting log tailer");
        // override default conf
        if(arguments != null) {
            if (null != arguments.getAccessLogFile() && !"".equals(arguments.getAccessLogFile())) {
                this.accessLogFile = arguments.getAccessLogFile();
            }

            if (null != arguments.getTrafficThreshold() && !"".equals(arguments.getTrafficThreshold())) {
                reqPerSecond = Integer.parseInt(arguments.getTrafficThreshold());
            }
        }

        File file = new File(this.accessLogFile);

        // check whether file exists and is readable
        if(!file.exists() && !file.canRead()) {
            LOGGER.error("File " + this.accessLogFile + " doesn't exist or it is not readable.");
            System.exit(1);
        }

        // set global application config
        // BAD PRACTISE: try to reduce global vars usage
        applicationConfig.setAccessLogFile(this.accessLogFile);
        applicationConfig.setReqestPerSecond(this.reqPerSecond);
        LOGGER.debug("Application configuration: access log file location: " + applicationConfig.getAccessLogFile()
            + " | requests per second: " + applicationConfig.getReqestPerSecond());

        TailerListener listener = new LogFileTailerListener(repository);
        this.tailer = Tailer.create(file, listener, 1000, true);
        LOGGER.debug("Started log tailer");
    }

    /**
     * Stops tailing access.log file
     */
    public void stopTailer() {
        LOGGER.debug("Stopping log tailer");
        this.tailer.stop();
    }
}
