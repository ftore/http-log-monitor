package com.example.httplogmonitor.monitor;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class LogAggregatorTask {
    @Scheduled(fixedRate = 1000)
    public void aggregate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        System.out.println(dateFormat.format(new Date()));
    }
}
