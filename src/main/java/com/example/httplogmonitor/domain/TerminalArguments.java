package com.example.httplogmonitor.domain;

import org.apache.commons.cli.CommandLine;

public class TerminalArguments {
    private String accessLogFile;
    private String trafficThreshold;

    public TerminalArguments(CommandLine commandLine) {
        this.accessLogFile = commandLine.getOptionValue("f");
        this.trafficThreshold = commandLine.getOptionValue("t");
    }

    public String getAccessLogFile() {
        return accessLogFile;
    }

    public void setAccessLogFile(String accessLogFile) {
        this.accessLogFile = accessLogFile;
    }

    public String getTrafficThreshold() {
        return trafficThreshold;
    }

    public void setTrafficThreshold(String trafficThreshold) {
        this.trafficThreshold = trafficThreshold;
    }
}
