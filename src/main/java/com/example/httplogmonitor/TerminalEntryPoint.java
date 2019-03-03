package com.example.httplogmonitor;

import com.example.httplogmonitor.domain.TerminalArguments;
import com.example.httplogmonitor.monitor.LogTailerComponent;
import com.example.httplogmonitor.service.TerminalArgumentsService;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Controller;

import javax.annotation.PreDestroy;
import java.util.Arrays;

@Controller
public class TerminalEntryPoint implements CommandLineRunner {

    @Autowired
    private TerminalArgumentsService argumentsService;

    @Autowired
    private LogTailerComponent logTailer;

    private final static String COMMAND_LINE_PREFIX = "java -jar http-log-monitor-0.0.1-SNAPSHOT.jar";

    private static final Logger LOGGER = LoggerFactory.getLogger(TerminalEntryPoint.class);

    @Override
    public void run(String... args) throws Exception {
        Options options = argumentsService.getArguments();

        if(Arrays.asList(args).contains("-h") || Arrays.asList(args).contains("--help")) {
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp( COMMAND_LINE_PREFIX, options, true );
            return;
        }

        CommandLineParser cmdLineParser = new DefaultParser();
        CommandLine commandLineGlobal = null;
        try {
            commandLineGlobal= cmdLineParser.parse(options, args);
        } catch(ParseException ex) {
            System.out.println(ex.getMessage());
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp( COMMAND_LINE_PREFIX, options, true );
            return;
        }

        TerminalArguments terminalArgs = new TerminalArguments(commandLineGlobal);
        this.logTailer.startTailer(terminalArgs);

        System.out.println("You can press Ctrl+C to shutdown application");
        //Thread.currentThread().join();
        //System.exit(0);
    }

    @PreDestroy
    public void onExit() {
        LOGGER.debug("### STOP FROM THE LIFECYCLE ###");
        this.logTailer.stopTailer();
    }
}
