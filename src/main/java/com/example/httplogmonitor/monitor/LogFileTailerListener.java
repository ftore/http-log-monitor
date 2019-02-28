package com.example.httplogmonitor.monitor;

import org.apache.commons.io.input.TailerListenerAdapter;

public class LogFileTailerListener extends TailerListenerAdapter {
    public void handle(String line) {
        System.out.println(line);
    }
}
