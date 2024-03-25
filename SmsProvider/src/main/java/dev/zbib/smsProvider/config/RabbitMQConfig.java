package dev.zbib.smsProvider.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    @Bean
    public Queue smsQueue() {
        return new Queue("sms-queue", false);
    }
}
