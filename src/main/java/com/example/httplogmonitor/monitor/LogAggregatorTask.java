package com.example.httplogmonitor.monitor;

import com.example.httplogmonitor.domain.AccessLogEntry;
import com.example.httplogmonitor.domain.Section;
import com.example.httplogmonitor.domain.StatusCode;
import com.example.httplogmonitor.repository.AccessLogEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

/**
 * Aggregates log entries to useful insight such as top visited pages and etc.
 *
 * @author arakhimoff@gmail.com
 */
@Component
public class LogAggregatorTask {

    @Autowired
    private AccessLogEntryRepository repository;

    /**
     *
     */
    @Scheduled(fixedRate = 10000)
    public void aggregate() {
        List<AccessLogEntry> accessLogEntryList = repository.findAllByTime();

        for(AccessLogEntry entry : accessLogEntryList) {
            System.out.println(entry);
        }
    }

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
}
