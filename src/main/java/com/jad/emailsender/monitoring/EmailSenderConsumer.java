package com.jad.emailsender.monitoring;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jad.emailsender.data.UserDTO;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
@Log4j2
public class EmailSenderConsumer implements MessageListener {

    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * This consumer listen to queued events into RabbitMQ and process them
     */
    @Override
    public void onMessage(Message message) {
        UserDTO userDTO = null;

        try {
            userDTO = objectMapper.readValue(new String(message.getBody()), UserDTO.class);
        } catch (IOException ex) {
            log.error("Failed to parse message body due to ", ex);
        }

        log.info("Ready to send email to {}", userDTO);

        // TODO: Implement logic here to send email
    }

}
