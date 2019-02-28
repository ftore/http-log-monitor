package com.example.httplogmonitor.domain;

import org.apache.commons.cli.CommandLine;

/**
 * Terminal arguments object. It will hold the following information:
 * - absolute path to access.log set by terminal argument -f
 * - traffic threshold set by terminal argument -t
 *
 * @author arakhimoff@gmail.com
 */
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
