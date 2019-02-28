package com.example.httplogmonitor.service;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.springframework.stereotype.Service;

/**
 * Reads the all valid command line arguments and shows help message if necessary.
 * 
 */
@Service
public class DefaultTerminalArgumentServiceImpl implements TerminalArgumentsService {
    @Override
    public Options getArguments() {
        Options options = new Options();

        final Option help = Option.builder("h")
                .longOpt("help")
                .hasArg(false)
                .desc("Print help message")
                .build();

        // absolute file path argument
        final Option file = Option.builder("f")
                .longOpt("file")
                .argName("file")
                .hasArg()
                .desc("Absolute access log file location (default: /tmp/access.log)")
                .build();

        // Requests per second argument
        final Option trafficThreshold = Option.builder("t")
                .longOpt("traffic")
                .argName("traffic")
                .hasArg()
                .desc("Traffic threshold per second (default: 10 requests per second")
                .build();

        //add all the options
        options.addOption(help);
        options.addOption(file);
        options.addOption(trafficThreshold);

        return options;
    }
}
