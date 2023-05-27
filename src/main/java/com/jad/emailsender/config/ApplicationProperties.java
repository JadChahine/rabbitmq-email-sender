package com.jad.emailsender.config;

import lombok.Data;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private final RabbitMqProperties rabbitMqProperties = new RabbitMqProperties();

    @Data
    public static class RabbitMqProperties {
        private String host;
        private String port;
        private String virtualHost;
        private String userName;
        private String password;
        private String exchangeName;
        private String routingKeyName;
        private String queueName;
    }

}