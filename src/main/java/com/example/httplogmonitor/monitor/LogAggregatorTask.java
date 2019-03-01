package com.example.httplogmonitor.monitor;

import com.example.httplogmonitor.domain.AccessLogEntry;
import com.example.httplogmonitor.domain.Section;
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
    @Scheduled(fixedRate = 10010)
    public void aggregateSections() {
        Collection<Section> topSections = repository.findTopSections();

        for(Section section : topSections) {
            System.out.println(section.getPage() + " | " + section.getCountNo());
        }
    }
}
