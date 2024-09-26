package dev.zbib.server.service;

import dev.zbib.server.model.request.SmsProviderRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import reactor.core.publisher.Mono;


public abstract class ABaseQueueSmsProviderService implements ISmsProviderService {

    protected final RabbitTemplate rabbitTemplate;
    private final String queueName;

    protected ABaseQueueSmsProviderService(RabbitTemplate rabbitTemplate, String queueName) {
        this.rabbitTemplate = rabbitTemplate;
        this.queueName = queueName;
    }

    @Override
    public Mono<String> sendSms(SmsProviderRequest smsProviderRequest) {
        rabbitTemplate.convertAndSend(queueName, smsProviderRequest.getMessage());
        return Mono.just("Message sent");
    }
}
