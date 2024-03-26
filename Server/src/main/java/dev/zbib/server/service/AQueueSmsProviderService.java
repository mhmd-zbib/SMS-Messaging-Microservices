package dev.zbib.server.service;

import dev.zbib.server.model.request.SmsProviderRequest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Mono;


/**
 * <h2>Abstract class for SMS queue providers</h2>
 * <p>This class implements the ISmsProviderService taking all the methods that all the providers do,
 * and here it uses them depending on its context. Using bean {@link RabbitTemplate} bean in config.
 */


public abstract class AQueueSmsProviderService implements ISmsProviderService {
    private final String queueName;

    @Autowired
    protected RabbitTemplate rabbitTemplate;


    protected AQueueSmsProviderService(String queueName) {
        this.queueName = queueName;
    }

    /**
     * <h3>SMS message sender</h3>
     * <p>This service handles sending sms to the queue provider. It passes the request params to the
     * post api from the smsProviderRequest</p>
     */

    @Override
    public Mono<String> sendSms(SmsProviderRequest smsProviderRequest) {
        rabbitTemplate.convertAndSend(queueName, smsProviderRequest.getMessage());
        return Mono.just("Message sent");
    }
}
