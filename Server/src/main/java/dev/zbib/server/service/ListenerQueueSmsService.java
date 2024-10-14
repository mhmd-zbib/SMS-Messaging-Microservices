package dev.zbib.server.service;

import dev.zbib.server.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class ListenerQueueSmsService extends aQueueSmsService {
    public ListenerQueueSmsService(RabbitTemplate rabbitTemplate) {
        super(rabbitTemplate);
    }

    @Override
    protected String getRoutingKey() {
        return RabbitMQConfig.ROUTING_KEY_SMS_LISTENER;
    }
}