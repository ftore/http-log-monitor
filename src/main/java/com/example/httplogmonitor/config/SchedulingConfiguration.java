package com.example.httplogmonitor.config;

import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@Profile("!test")
public class SchedulingConfiguration {
}
