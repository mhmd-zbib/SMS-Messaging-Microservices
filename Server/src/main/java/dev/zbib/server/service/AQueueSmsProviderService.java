package dev.zbib.server.service;

import dev.zbib.server.model.request.SmsProviderRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;


public abstract class AQueueSmsProviderService implements ISmsProviderService {
    private final String queueName;

    @Autowired
    protected RabbitTemplate rabbitTemplate;


    protected AQueueSmsProviderService(String queueName) {
        this.queueName = queueName;
    }

    @Override
    public Mono<String> sendSms(SmsProviderRequest smsProviderRequest) {
        rabbitTemplate.convertAndSend(queueName, smsProviderRequest.getMessage());
        return Mono.just("Message sent");
    }
}
