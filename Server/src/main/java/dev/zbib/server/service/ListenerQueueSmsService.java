package dev.zbib.server.service;

import dev.zbib.server.config.RabbitMQConfig;
import org.springframework.stereotype.Service;

@Service
public class ListenerQueueSmsService extends AQueueSmsService {


    @Override
    protected String getRoutingKey() {
        return RabbitMQConfig.ROUTING_KEY_SMS_LISTENER;
    }


}