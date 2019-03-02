package com.example.httplogmonitor.domain;

public class ApplicationConfig {
    private String accessLogFile;
    private int reqestPerSecond;

    public String getAccessLogFile() {
        return accessLogFile;
    }

    public void setAccessLogFile(String accessLogFile) {
        this.accessLogFile = accessLogFile;
    }

    public int getReqestPerSecond() {
        return reqestPerSecond;
    }

    public void setReqestPerSecond(int reqestPerSecond) {
        this.reqestPerSecond = reqestPerSecond;
    }
}
