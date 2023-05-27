package com.jad.emailsender.monitoring;

import com.jad.emailsender.data.UserDTO;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static com.jad.emailsender.config.RabbitMQConfiguration.RABBIT_SEND_EMAIL_TEMPLATE_NAME;

@Component
@Log4j2
public class EmailSenderProducer {

    @Autowired
    @Qualifier(RABBIT_SEND_EMAIL_TEMPLATE_NAME)
    private RabbitTemplate rabbitTemplate;

    /**
     * Each 10 seconds an event will be queued into RabbitMQ to be consumed by {@link EmailSenderConsumer}
     */
    @Scheduled(fixedRate = 10000)
    public void generate() {
        try {
            rabbitTemplate.convertAndSend(
                    UserDTO.builder()
                            .name("Your name")
                            .emailAddress("youemail@email.com")
                            .build()
            );

            log.info("User email event queued successfully");
        } catch (Exception ex) {
            log.error("Failed to queue user email event due to ", ex);
        }
    }

}
