package com.esprit.jobfinder.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

@Configuration
@EnableScheduling
public class SchedulerConfig {

    @Scheduled(fixedRate = 60000)
    public void scheduleTask() {
        System.out.println("Scheduled task running every 60 seconds");
    }
}
