package dev.zbib.smsProvider.service;

import dev.zbib.smsProvider.model.request.SmsProviderRequest;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class MessageListener {

    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public MessageListener(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = "sms-listener")
    public void listenerConsumer(SmsProviderRequest messageReq) {
        System.out.println("Listener got:  " + messageReq.getMessage());
    }

    @Scheduled(fixedRate = 10000)
    public void cronConsumer() {
        while (true) {
            var message = rabbitTemplate.receive("sms-cron");
            if (message != null) {
                String messageBody = new String(message.getBody());
                System.out.println("Cron got: " + messageBody);
            } else {
                break;
            }
        }
    }
}