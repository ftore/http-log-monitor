package com.example.httplogmonitor.monitor;

import com.example.httplogmonitor.domain.TerminalArguments;
import org.apache.commons.io.input.Tailer;
import org.apache.commons.io.input.TailerListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class LogTailer {

    @Value("${appconfig.file.accesslog}")
    private String accessLogFile;

    @Value("${appconfig.requests-per-second}")
    private int reqPerSecond;

    private Tailer tailer;

    public void startTailer(TerminalArguments arguments) {

        // override default conf
        if(arguments != null) {
            if (null != arguments.getAccessLogFile() && !"".equals(arguments.getAccessLogFile())) {
                this.accessLogFile = arguments.getAccessLogFile();
            }

            if (null != arguments.getTrafficThreshold() && !"".equals(arguments.getTrafficThreshold())) {
                reqPerSecond = Integer.parseInt(arguments.getTrafficThreshold());
            }
        }

        File file = new File(this.accessLogFile);

        TailerListener listener = new LogFileTailerListener();
        this.tailer = Tailer.create(file, listener, 1000);

    }

    public void stopTailer() {
        this.tailer.stop();
    }
}
