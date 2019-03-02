package com.example.httplogmonitor;

import com.example.httplogmonitor.domain.ApplicationConfig;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HttpLogMonitorApplication {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(HttpLogMonitorApplication.class);
        app.setBannerMode(Banner.Mode.OFF);
        app.run(args);
    }

    @Bean
    public ApplicationConfig applicationConfig() {
        return new ApplicationConfig();
    }
}
