package dev.zbib.smsProvider.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConsumer {

    @RabbitListener(queues = "sms-queue")
    public void receiveMessage(String message) {
        System.out.println("Received message: " + message);
    }
}