package edu.rutmiit.demo.notificationservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {
        "edu.rutmiit.demo.notificationservice.config",
        "edu.rutmiit.demo.notificationservice.handler",
        "edu.rutmiit.demo.notificationservice.listener",
        "edu.rutmiit.demo.notificationservice.dto"
})
public class NotificationServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(NotificationServiceApplication.class, args);
    }
}