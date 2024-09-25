package dev.zbib.server.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;


@Service
public class QueueSmsProviderProducerService {


    private final RabbitTemplate rabbitTemplate;
    private static final Logger logger = LoggerFactory.getLogger(QueueSmsProviderProducerService.class);

    @Autowired
    public QueueSmsProviderProducerService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }



    public Mono<String> sendSms() {
        rabbitTemplate.convertAndSend("sms-queue", "hello");
        return Mono.just("Message sent");
    }
}
