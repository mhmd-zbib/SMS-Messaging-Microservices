package dev.zbib.smsProvider.service;

import dev.zbib.smsProvider.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledQueueSmsService {

    private static final int MAX_MESSAGES = 5;

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public ScheduledQueueSmsService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Scheduled(fixedRate = 10000)
    public void cronConsumer() {
        int count = 0;
        while (count < MAX_MESSAGES) {
            var message = rabbitTemplate.receive(RabbitMQConfig.CRON_QUEUE);
            if (message == null) break;

            String messageBody = new String(message.getBody());
            System.out.println("Cron got: " + messageBody);
            count++;
        }
    }
}
