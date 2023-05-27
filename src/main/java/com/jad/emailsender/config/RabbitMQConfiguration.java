package com.jad.emailsender.config;

import com.jad.emailsender.monitoring.EmailSenderConsumer;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;

@Configuration
public class RabbitMQConfiguration {

    public static final String RABBIT_SEND_EMAIL_CONNECTION_FACTORY = "sendEmailRabbitConnectionFactory";
    public static final String RABBIT_SEND_EMAIL_TEMPLATE_NAME = "sendEmailRabbitTemplate";

    @Autowired
    private EmailSenderConsumer emailSenderConsumer;

    @Autowired
    private ApplicationProperties applicationProperties;

    private ApplicationProperties.RabbitMqProperties getRabbitMqProperties() {
        return applicationProperties.getRabbitMqProperties();
    }

    @Bean(RABBIT_SEND_EMAIL_CONNECTION_FACTORY)
    public ConnectionFactory sendEmailRabbitConnectionFactory() {
        String host = Objects.requireNonNullElse(getRabbitMqProperties().getHost(), "localhost");
        int port = Objects.requireNonNullElse(NumberUtils.toInt(getRabbitMqProperties().getPort()), 5672);
        String virtualHost = Objects.requireNonNull(getRabbitMqProperties().getVirtualHost());
        String userName = Objects.requireNonNull(getRabbitMqProperties().getUserName());
        String password = Objects.requireNonNull(getRabbitMqProperties().getPassword());

        CachingConnectionFactory rabbitConnectionFactory = new CachingConnectionFactory(host);
        rabbitConnectionFactory.setPort(port);
        rabbitConnectionFactory.setUsername(userName);
        rabbitConnectionFactory.setPassword(password);
        rabbitConnectionFactory.setVirtualHost(virtualHost);

        return rabbitConnectionFactory;
    }

    @Bean(RABBIT_SEND_EMAIL_TEMPLATE_NAME)
    public RabbitTemplate sendEmailRabbitTemplate(@Qualifier(RABBIT_SEND_EMAIL_CONNECTION_FACTORY) ConnectionFactory rabbitConnectionFactory) {
        String exchange = Objects.requireNonNull(getRabbitMqProperties().getExchangeName());
        String routingKey = Objects.requireNonNull(getRabbitMqProperties().getRoutingKeyName());

        RabbitTemplate rabbitTemplate = new RabbitTemplate(rabbitConnectionFactory);
        rabbitTemplate.setExchange(exchange);
        rabbitTemplate.setRoutingKey(routingKey);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());

        return rabbitTemplate;
    }

    @Bean
    public SimpleMessageListenerContainer sendEmailRabbitListener(@Qualifier(RABBIT_SEND_EMAIL_CONNECTION_FACTORY) ConnectionFactory rabbitConnectionFactory) {
        String queueName = Objects.requireNonNull(getRabbitMqProperties().getQueueName());

        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(rabbitConnectionFactory);
        container.setAcknowledgeMode(AcknowledgeMode.AUTO);
        container.setQueueNames(queueName);
        container.setMessageListener(emailSenderConsumer);

        return container;
    }


}
