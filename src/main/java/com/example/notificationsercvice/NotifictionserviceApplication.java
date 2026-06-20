package com.example.notificationsercvice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.example.notification")
@EnableJpaRepositories(basePackages = "com.example.notification.repository")
@EntityScan(basePackages = "com.example.notification.entity")
public class NotifictionserviceApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotifictionserviceApplication.class, args);
    }
}