package com.example.httplogmonitor.monitor;

import com.example.httplogmonitor.domain.*;
import com.example.httplogmonitor.repository.AccessLogEntryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * Aggregates log entries to useful insight such as top visited pages and etc.
 *
 * @author arakhimoff@gmail.com
 */
@Component
public class LogAggregatorTask {
    private static final Logger LOGGER = LoggerFactory.getLogger(LogAggregatorTask.class);

    @Autowired
    private AccessLogEntryRepository repository;

    @Autowired
    private ApplicationConfig applicationConfig;

    private boolean alert = false;

    private final String ANSI_RESET = "\u001B[0m";
    private final String ANSI_RED = "\u001B[31m";
    private final String ANSI_GREEN = "\u001B[32m";

    /**
     * Aggregates top sections for last 10s.
     * For example, the section for "/pages/create" is "/pages"
     *
     */
    @Scheduled(fixedRate = 10000)
    public void aggregateSections() {
        Collection<Section> topSections = repository.findTopSections();
        System.out.println("TOP 10 SECTIONS");

        String leftAlignFormat = "| %-23s | %-10d |%n";

        System.out.format("+-------------------------+------------+%n");
        System.out.format("| Section                 | Count      |%n");
        System.out.format("+-------------------------+------------+%n");
        for(Section section : topSections) {
            System.out.format(leftAlignFormat, section.getPage(), section.getCountNo());
        }
        System.out.format("+-------------------------+------------+%n");
        System.out.println();
    }

    /**
     * Aggregates status codes by count in the following format:
     * 2XX - count
     * 3XX - count
     * 4XX - count
     * 5XX - count
     *
     */
    @Scheduled(fixedRate = 10000)
    public void aggregateStatusCodes() {
        Collection<StatusCode> statusCodeCounts = repository.findStatusCounts();
        System.out.println("STATUS CODE COUNTS");
        String leftAlignFormat = "| %-16s | %-10d |%n";

        System.out.format("+------------------+------------+%n");
        System.out.format("| Status Codes     | Count      |%n");
        System.out.format("+------------------+------------+%n");
        for(StatusCode status : statusCodeCounts) {
            System.out.format(leftAlignFormat, status.getStatusCode() + "XX", status.getCountNo());
        }
        System.out.format("+------------------+------------+%n");
        System.out.println();
    }

    /**
     * Aggregates total throughput in KB
     */
    @Scheduled(fixedRate = 10000)
    public void aggregateThroughput() {
        Throughput throughput = repository.findTotalThroughput();
        System.out.println("TOTAL BYTES TRANSFERRED");

        long totalThroughput = null != throughput ? throughput.getTotalThroughput() : 0;

        System.out.format("+----------------------+------------+%n");
        System.out.format("| Total Throughput     | " + totalThroughput + "KB    |%n");
        System.out.format("+----------------------+------------+%n");
        System.out.println();
    }

    @Scheduled(fixedRate = 120000)
    public void aggregateHits() {
        LOGGER.debug("Application config: " + applicationConfig.getAccessLogFile() + " | "
                + applicationConfig.getReqestPerSecond());

        Hits hits = repository.findTotalHits();

        if(null != hits) {
            // we divide to total hits to 120 seconds (2 minutes)
            // to find out average requests pre second
            long averageRequestPerSecond = hits.getTotalHits() / 120;

            // if average request are higher than threshold
            // show the notification
            if(!alert && averageRequestPerSecond >= applicationConfig.getReqestPerSecond()) {
                alert = true;
                System.out.printf(ANSI_RED + "High traffic generated an alert - { hits = %s, triggered at %s}%n"
                        + ANSI_RESET,
                        averageRequestPerSecond,
                        LocalDateTime.now());
            }

            if(alert && averageRequestPerSecond < applicationConfig.getReqestPerSecond()) {
                alert = false;
                System.out.printf(ANSI_GREEN + "High traffic alert recovered - { hits = %s, recovered at %s}%n"
                        + ANSI_RESET,
                        averageRequestPerSecond,
                        LocalDateTime.now().toString());
            }
        }
    }
}
