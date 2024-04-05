package dev.zbib.smsProvider.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.zbib.smsProvider.config.RabbitMQConfig;
import dev.zbib.smsProvider.model.request.SmsProviderRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledQueueSmsService {

    private static final int MAX_MESSAGES = 5;

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper jacksonObjectMapper;

    @Autowired
    public ScheduledQueueSmsService(RabbitTemplate rabbitTemplate, ObjectMapper jacksonObjectMapper) {
        this.rabbitTemplate = rabbitTemplate;
        this.jacksonObjectMapper = jacksonObjectMapper;
    }

    @Scheduled(fixedRate = 10000)
    public void cronConsumer() {
        int count = 0;
        while (count < MAX_MESSAGES) {
            var message = rabbitTemplate.receive(RabbitMQConfig.CRON_QUEUE);
            if (message == null) break;

            SmsProviderRequest smsRequest = convertToSmsProviderRequest(new String(message.getBody()));
            System.out.println("Cron got: " + smsRequest.toString());
            count++;
        }
    }


    private SmsProviderRequest convertToSmsProviderRequest(String messageBody) {
        try {
            return jacksonObjectMapper.readValue(messageBody, SmsProviderRequest.class);
        } catch (JsonProcessingException e) {
            System.err.println("Error converting message to SmsProviderRequest: " + e.getMessage());
            return null;
        }
    }

}
