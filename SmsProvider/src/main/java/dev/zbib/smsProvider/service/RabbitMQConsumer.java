package dev.zbib.smsProvider.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class RabbitMQConsumer {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitMQConsumer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = "sms-listener")
    public void listenerConsumer(String message) {
        System.out.println("Received message:  " + message);
    }

    @Scheduled(fixedRate = 10000)
    public void cronConsumer() {
        while (true) {
            var message = rabbitTemplate.receive("sms-cron");
            if (message != null) {
                String messageBody = new String(message.getBody());
                System.out.println("Consumed message: " + messageBody);
            } else {
                break;
            }
        }
    }
}