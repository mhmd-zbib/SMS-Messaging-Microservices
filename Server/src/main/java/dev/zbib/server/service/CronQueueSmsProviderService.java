package dev.zbib.server.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CronQueueSmsProviderService extends AQueueSmsProviderService {

    @Autowired
    protected CronQueueSmsProviderService(RabbitTemplate rabbitTemplate) {
        super(rabbitTemplate, "sms-cron");
    }
}
