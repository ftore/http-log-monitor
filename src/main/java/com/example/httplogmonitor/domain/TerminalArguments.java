package com.example.httplogmonitor.domain;

import org.apache.commons.cli.CommandLine;

public class TerminalArguments {
    String accessLogFile;

    public TerminalArguments(CommandLine commandLine) {
        this.accessLogFile = commandLine.getOptionValue("f");
    }
}
