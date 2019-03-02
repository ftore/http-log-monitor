package com.example.httplogmonitor.monitor;

import com.example.httplogmonitor.domain.AccessLogEntry;
import com.example.httplogmonitor.repository.AccessLogEntryRepository;
import org.apache.commons.io.input.TailerListenerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Listens for each line of new log entry and saves it to database.
 *
 * @author arakhimoff@gmail.com
 */
public class LogFileTailerListener extends TailerListenerAdapter {
    private AccessLogEntryRepository repository;

    private static final Logger LOGGER = LoggerFactory.getLogger(LogFileTailerListener.class);

    public LogFileTailerListener(AccessLogEntryRepository  repository) {
        this.repository = repository;
    }

    /**
     * Save logs to database line by line
     *
     * @param line log line in access log file
     */
    public void handle(String line) {
        LOGGER.debug("Received a log line: " + line);
        AccessLogEntry accessLogEntry = new AccessLogEntry(line);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        accessLogEntry.setCreateTime(dateFormat.format(new Date()));

        this.repository.save(accessLogEntry);
    }
}
