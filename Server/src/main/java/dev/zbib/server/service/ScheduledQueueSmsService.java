package dev.zbib.server.service;

import dev.zbib.server.config.RabbitMQConfig;
import org.springframework.stereotype.Service;

@Service
public class ScheduledQueueSmsService extends AQueueSmsService {

    @Override
    protected String getRoutingKey() {
        return RabbitMQConfig.ROUTING_KEY_SMS_CRON;
    }
}
