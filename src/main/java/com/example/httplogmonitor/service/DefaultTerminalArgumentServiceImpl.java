package com.example.httplogmonitor.service;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.springframework.stereotype.Service;

@Service
public class DefaultTerminalArgumentServiceImpl implements TerminalArgumentsService {
    @Override
    public Options getArguments() {
        Options options = new Options();

        final Option help = Option.builder("h")
                .longOpt("help")
                .hasArg(false)
                .desc("print help message")
                .build();

        //options with arguments
        final Option file = Option.builder("f")
                .longOpt("file")
                .argName("file")
                .hasArg()
                .desc("access log file")
                .build();

        //add all the options
        options.addOption(help);

        return options;
    }
}
