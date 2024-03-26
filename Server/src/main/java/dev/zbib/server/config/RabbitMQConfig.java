package dev.zbib.server.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {

    @Bean
    public Queue smsListenerQueue() {
        return new Queue("sms-listener", true);
    }

    @Bean
    public Queue smsCronQueue() {
        return new Queue("sms-cron", true);
    }
}
