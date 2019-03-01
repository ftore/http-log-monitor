package com.example.httplogmonitor.repository;

import com.example.httplogmonitor.domain.AccessLogEntry;
import com.example.httplogmonitor.domain.Section;
import com.example.httplogmonitor.domain.StatusCode;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;

public interface AccessLogEntryRepository extends CrudRepository<AccessLogEntry, Long> {

    /**
     * Fetches latest log entries within 10 seconds window
     * @return list of access log entries
     */
    @Query(value = "SELECT * FROM ACCESSLOGENTRY WHERE DATEDIFF('SECOND', CREATETIME, NOW()) < 10", nativeQuery = true)
    List<AccessLogEntry> findAllByTime();

    @Query(value =
            "SELECT SUBSTRING(URL, 0, LOCATE('/', URL, 2) - 1) AS page, COUNT(*) AS countNo " +
            "FROM ACCESSLOGENTRY " +
            "WHERE DATEDIFF('SECOND', CREATETIME, NOW()) < 10 " +
            "GROUP BY page ORDER BY countNo DESC LIMIT 10", nativeQuery = true)
    Collection<Section> findTopSections();

    @Query(value =
            "SELECT SUBSTRING(STATUS, 0, 1) AS statusCode, COUNT(*) AS countNo " +
            "FROM ACCESSLOGENTRY " +
            "WHERE DATEDIFF('SECOND', CREATETIME, NOW()) < 10 " +
            "GROUP BY statusCode", nativeQuery = true)
    Collection<StatusCode> findStatusCounts();
}
