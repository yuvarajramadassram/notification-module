package com.example.notification.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean(name = "notificationExecutor")
    public Executor notificationExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);      // Base threads
        executor.setMaxPoolSize(10);     // Max threads under load
        executor.setQueueCapacity(500);  // Queue size capacity
        executor.setThreadNamePrefix("NotifyWorker-");
        executor.initialize();
        return executor;
    }
}