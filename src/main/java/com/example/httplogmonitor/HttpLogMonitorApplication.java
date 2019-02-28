package com.example.httplogmonitor;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.CountDownLatch;

@SpringBootApplication
@EnableScheduling
public class HttpLogMonitorApplication {

    @Bean
    public CountDownLatch closeLatch() {
        return new CountDownLatch(1);
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(HttpLogMonitorApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }


}
