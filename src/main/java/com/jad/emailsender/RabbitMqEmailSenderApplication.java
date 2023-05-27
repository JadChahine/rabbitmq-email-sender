package com.jad.emailsender;

import com.jad.emailsender.config.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableConfigurationProperties(ApplicationProperties.class)
@EnableScheduling
public class RabbitMqEmailSenderApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitMqEmailSenderApplication.class, args);
    }

}
