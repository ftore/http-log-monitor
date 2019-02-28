package com.example.httplogmonitor.monitor;

import com.example.httplogmonitor.domain.AccessLogEntry;
import com.example.httplogmonitor.repository.AccessLogEntryRepository;
import org.apache.commons.io.input.TailerListenerAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Listens for each line of new log entry and saves it to database.
 *
 * @author arakhimoff@gmail.com
 */
public class LogFileTailerListener extends TailerListenerAdapter {
    private AccessLogEntryRepository repository;

    public LogFileTailerListener(AccessLogEntryRepository  repository) {
        this.repository = repository;
    }

    /**
     * Save logs to database line by line
     *
     * @param line log line in access log file
     */
    public void handle(String line) {
        AccessLogEntry accessLogEntry = new AccessLogEntry(line);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        accessLogEntry.setCreateTime(dateFormat.format(new Date()));

        this.repository.save(accessLogEntry);
    }
}
