package dev.zbib.smsProvider.service;

import dev.zbib.smsProvider.config.RabbitMQConfig;
import dev.zbib.smsProvider.model.request.SmsProviderRequest;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@Component
public class ListenerQueueSmsService {

    @RabbitListener(queues = RabbitMQConfig.LISTENER_QUEUE)
    public void listenerConsumer(SmsProviderRequest messageReq) {
        System.out.println("Listener got:  " + messageReq.getMessage());
    }
}