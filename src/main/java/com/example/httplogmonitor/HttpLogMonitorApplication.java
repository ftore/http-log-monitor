package com.example.httplogmonitor;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class HttpLogMonitorApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(HttpLogMonitorApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

}
