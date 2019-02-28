package com.example.httplogmonitor.repository;

import com.example.httplogmonitor.domain.AccessLogEntry;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AccessLogEntryRepository extends CrudRepository<AccessLogEntry, Long> {

    /**
     * Fetches latest log entries within 10 seconds window
     * @return list of access log entries
     */
    @Query(value = "SELECT * FROM ACCESSLOGENTRY WHERE DATEDIFF('SECOND', CREATETIME, NOW()) < 10", nativeQuery = true)
    List<AccessLogEntry> findAllByTime();
}
